package com.lik.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.lik.entity.Log;
import com.lik.service.LogService;
import com.lik.util.PropertiesUtil;
import com.lik.util.StringUtil;

/**
 * 博客索引类
 * @author xiao
 *
 */
public class LogIndex {
	public static final String INDEXPATH = PropertiesUtil.getValue("indexPath");
	private static Directory dir;
	@Resource
	private LogService logService;

	/**
	 * 获取IndexWriter实例
	 * 
	 * @throws IOException
	 */
	private IndexWriter getWriter() throws IOException {
		dir = FSDirectory.open(Paths.get(INDEXPATH));
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(dir, iwc);
		return writer;
	}
	/**
	 * 添加日志索引
	 */
	public void addIndex(Log log) throws Exception{
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(log.getId()), Field.Store.YES));
		doc.add(new TextField("title", log.getTitle(), Field.Store.YES));
		doc.add(new TextField("content", log.getContentNoTag(), Field.Store.YES));
		doc.add(new StringField("createTime", log.getCreateTime(), Field.Store.YES));
		writer.addDocument(doc);
		writer.close();
	}
	/**
	 * 查询日志信息 
	 */
	public List<Log> searchBlog(String q)throws Exception{
		dir = FSDirectory.open(Paths.get(INDEXPATH));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is = new IndexSearcher(reader);
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		QueryParser parser = new QueryParser("title",analyzer);
		Query query = parser.parse(q);
		
		QueryParser parser2 = new QueryParser("content",analyzer);
		Query query2 = parser2.parse(q);
		
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
		
		TopDocs hits = is.search(booleanQuery.build(), 100);
		
		QueryScorer scorer = new QueryScorer(query);
		
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
		
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter,scorer);
		
		highlighter.setTextFragmenter(fragmenter);//设置显示片段
		
		List<Log> logList = new LinkedList<Log>();
		for (ScoreDoc scoreDoc:hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			Log log = new Log();
			log.setId(Integer.parseInt(doc.get("id")));
			log.setCreateTime(doc.get("createTime"));
			String title = doc.get("title");
			String content = doc.get("content");
			//String content = StringEscapeUtils.escapeHtml(doc.get("content"));//对文本标签进行过滤转义
			if(title!=null){
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
				String hTitle = highlighter.getBestFragment(tokenStream, title);
				if (StringUtil.isEmpty(hTitle)) {
					log.setTitle(title);
				}else{
					log.setTitle(hTitle);
				}
			}
			if(content!=null){
				TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
				String hContent = highlighter.getBestFragment(tokenStream, content);
				if (StringUtil.isEmpty(hContent)) {
					if (content.length()>200) {
						log.setContent(content.substring(0,200));
					}else{
						log.setContent(content);
					}
				}else{
					log.setContent(hContent);
				}
			}
			logList.add(log);
		}
		return logList;
	}
	/**
	 * 删除 在合并后 数据量小时使用
	 */
	public void deleteIndex(String id) throws Exception {
		IndexWriter writer = getWriter();
		writer.deleteDocuments(new Term("id", id));
		writer.forceMergeDeletes();// 强制删除
		writer.commit();
		writer.close();
	}

	/**
	 * 测试更新
	 */
	public void updateIndex(Log log) throws Exception {
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(log.getId()), Field.Store.YES));
		doc.add(new TextField("title", log.getTitle(), Field.Store.YES));
		doc.add(new TextField("content", log.getContentNoTag(), Field.Store.YES));
		doc.add(new StringField("createTime", log.getCreateTime(), Field.Store.YES));
		writer.updateDocument(new Term("id", String.valueOf(log.getId())), doc);
		writer.commit();
		writer.close();
	}
}
