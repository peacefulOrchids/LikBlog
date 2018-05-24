;
(function($, window, document, undefined) {
	'use strict';
	$.fn.myEmoji = function(options) {
		var defaults = {
			emojiconfig: {
				tieba: {
					name: "���ɱ���",
					path: "img/tieba/",
					maxNum: 50,
					file: ".jpg",
					placeholder: ":{alias}:",
					alias: {
						1: "hehe",
						2: "haha",
						3: "tushe",
						4: "a",
						5: "ku",
						6: "lu",
						7: "kaixin",
						8: "han",
						9: "lei",
						10: "heixian",
						11: "bishi",
						12: "bugaoxing",
						13: "zhenbang",
						14: "qian",
						15: "yiwen",
						16: "yinxian",
						17: "tu",
						18: "yi",
						19: "weiqu",
						20: "huaxin",
						21: "hu",
						22: "xiaonian",
						23: "neng",
						24: "taikaixin",
						25: "huaji",
						26: "mianqiang",
						27: "kuanghan",
						28: "guai",
						29: "shuijiao",
						30: "jinku",
						31: "shengqi",
						32: "jinya",
						33: "pen",
						34: "aixin",
						35: "xinsui",
						36: "meigui",
						37: "liwu",
						38: "caihong",
						39: "xxyl",
						40: "taiyang",
						41: "qianbi",
						42: "dnegpao",
						43: "chabei",
						44: "dangao",
						45: "yinyue",
						46: "haha2",
						47: "shenli",
						48: "damuzhi",
						49: "ruo",
						50: "OK"
					},
					title: {
						1: "�Ǻ�",
						2: "����",
						3: "����",
						4: "��",
						5: "��",
						6: "ŭ",
						7: "����",
						8: "��",
						9: "��",
						10: "����",
						11: "����",
						12: "������",
						13: "���",
						14: "Ǯ",
						15: "����",
						16: "����",
						17: "��",
						18: "��",
						19: "ί��",
						20: "����",
						21: "��~",
						22: "Ц��",
						23: "��",
						24: "̫����",
						25: "����",
						26: "��ǿ",
						27: "��",
						28: "��",
						29: "˯��",
						30: "����",
						31: "����",
						32: "����",
						33: "��",
						34: "����",
						35: "����",
						36: "õ��",
						37: "����",
						38: "�ʺ�",
						39: "��������",
						40: "̫��",
						41: "Ǯ��",
						42: "����",
						43: "�豭",
						44: "����",
						45: "����",
						46: "haha",
						47: "ʤ��",
						48: "��Ĵָ",
						49: "��",
						50: "OK"
					}
				}
				//, AcFun: {
				// 	name: "AcFun����",
				// 	path: "img/AcFun/",
				// 	maxNum: 54,
				// 	file: ".png"
				// }
			},
			postFunction: function() {
				alert(InputText.html());
				console.log(InputText.html());
			}
		};
		var opts = $.extend(defaults, options);
		var emojiconfig = opts.emojiconfig;
		var plBox = $(this);
		var InputBox = plBox.find('.Input_Box');
		var faceDiv = plBox.find('.faceDiv');
		var InputText = InputBox.find('.Input_text');
		var InputFoot = InputBox.find('.Input_Foot');
		var imgBtn = InputFoot.find('.imgBtn');
		/**
		 * off����ΪԪ�ذ�һ�����¼�������,�ٴθ���Ԫ�������ͬ�¼�ʱ�����ۼӰ�
		 */
		imgBtn.off('click').on('click',
			function() {
				var emojiContainer = faceDiv.find('.emoji_container');
				if (emojiContainer.children().length <= 0) {
					faceDiv.css({
						width: Math.floor(InputText.width() / 30) * 30 + 'px',
						display: 'block'
					});
					for (var emojilist in emojiconfig) {
						emojiContainer.append('<section class="for_' + emojilist + '"></section>');
						faceDiv.find('.emoji_tab').append('<a data-target="for_' + emojilist + '">' + emojiconfig[emojilist].name + '</a>');
						for (var i = 1; i <= emojiconfig[emojilist].maxNum; i++) {
							if (emojiContainer.find('.for_' + emojilist) !== undefined) {
								emojiContainer.find('.for_' + emojilist).append('<a class="_img"><img src="' + emojiconfig[emojilist].path + i + emojiconfig[emojilist].file + '" alt="" data-alias="'+(emojiconfig[emojilist].alias == undefined ? (i+emojiconfig[emojilist].file) : emojiconfig[emojilist].alias[i])+'" title="' + (emojiconfig[emojilist].title == undefined ? '' : emojiconfig[emojilist].title[i]) + '" /></a>');
							}
						}
					}
					faceDiv.find('.emoji_container section')[0].style.display = 'block';
					faceDiv.find('.emoji_tab a')[0].className += 'active';
					faceDiv.find('.emoji_container ._img').on('click', function() {
						if (InputText[0].nodeName === 'DIV') {
							InputText.append(this.innerHTML);
						} else {
							InputText.append('[' + $(this).find('img').attr('data-alias')+']');
						}

					});
					faceDiv.find('.emoji_tab a').on('click', function() {
						$(this).parent().prev().find('section').hide();
						faceDiv.find('.emoji_container .' + $(this).attr('data-target')).show();
						faceDiv.find('.emoji_tab a').removeClass('active');
						this.className += ' active';
					});
				} else {
					faceDiv.toggle();
				}
			}
		);

		InputFoot.find('.postBtn').on('click', opts.postFunction);
		$(document).on('click', function(e) {
			if ((faceDiv.find($(e.target)).length) <= 0 && (InputBox.find($(e.target)).length <= 0)) {
				faceDiv.hide();
			}
		});
	};
})(jQuery, window, document);