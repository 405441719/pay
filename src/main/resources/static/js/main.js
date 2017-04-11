$("input:not(input[type='text'],input[type='password']),a").focus(function () {
    this.blur();
});
/* placeholder 兼容 */
var JPlaceHolder = {
    //检测
    _check: function () {
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init: function () {
        if (!this._check()) {
            this.fix();
        }
    },
    //修复
    fix: function () {
        jQuery(':input[placeholder]').each(function (index, element) {
            var self = $(this), txt = self.attr('placeholder');
            self.val('');
            // self.wrap($('<div class="phd"></div>').css({position:'relative', zoom:'1',display:'inline-block', border:'none', background:'none', padding:'none', margin:'none'}));
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span class="phds"></span>').text(txt).css({
                position: 'absolute',
                top: pos.top,
                left: pos.left + 30,
                height: h,
                lineHeight: h + 'px',
                paddingLeft: paddingleft,
                color: '#aaa'
            }).appendTo(self.parent());
            self.focusin(function (e) {
                holder.hide();
            }).focusout(function (e) {
                if (!self.val()) {
                    holder.show();
                }
            });
            holder.click(function (e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
/* ajax二次封装 */
var ajax = {
    submit: function (url, parameter, successCallBack, errorCallBack, type) {
        layer.closeAll('tips');
        var paramObj = {
            url: url,
            cache: false,
            data: parameter,
            async: true,
            success: function (response) {
                if (response == "" || response == null) {
                    response = null;
                } /*else {
                    response = jQuery.parseJSON(response);
                }*/
                if (successCallBack) {
                    successCallBack(response);
                }
                response = null;
            },
            error: function (jqXHR, textStatus, exception) {
                if (errorCallBack) {
                    errorCallBack(jqXHR, textStatus, exception);
                } else if (jqXHR.responseText) {
                    //alert(jqXHR.responseText);
                } else {
                    //alert("[Debug][Ajax Error]" + this.url); //TODO:Remove this dialog
                    //debugger;
                }
            }
        };
        if (type == "GET") {
            paramObj.type = "GET";
        } else {
            paramObj.type = "POST";
        }
        paramObj.url = this.getCultureUrl(paramObj.url);
        return $.ajax(paramObj);
    },
    getCultureUrl: function (url) {
        if (url.indexOf("http") == 0) {
        } else {
            var culUrl = "";
            if (url.indexOf(culUrl) != 0) {
                url = culUrl + url;
            }
        }
        return url;
    }
}
var isPhone = function (phone) {
    var reg = /^1[3|5|8|4|7]{1}[0-9]{1}[0-9]{8}$/;
    if (!reg.test(phone)) return false;
    return true;
}
/*验证码倒计时*/
var CountDown = {
    timer: null,
    val: 59,
    Run: function ($ell) {
        var _self = this, con = "重新获取", $el = $ell;
        if (this.val == 59) {
            $el.attr("disabled", "disabled").text("(60秒后)" + con);
        }
        setTimeout(function () {
            if (_self.val == 0) {
                $el.removeAttr("disabled unclick").text(con);
                _self.val = 59;
                return;
            }
            $el.text("(" + _self.val + "秒后)" + con);
            _self.val -= 1;
            _self.Run($el);
        }, 1000);
    }
};
/*验证码窗口显示/隐藏*/
var codebox = {
    show: function () {
        $(".code-con,#bgcode").fadeIn(100);
        $(".code-yzm").click();
    },
    hide: function () {
        $("#ycode").val('');
        $(".code-con,#bgcode").fadeOut(100);
    }
}
/*保留2位小数*/
function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return parseFloat(s);
}
function toDecimal2str(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}
function fmoney(s, n){
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
        r = s.split(".")[1];
    t = "";
    for(i = 0; i < l.length; i ++ )
    {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}
/*打开iframe*/
var openifm = function (title, content, height) {
    width = '480px';
    if (height == '' || height == null || height == undefined || height == false) width = '270px';
    layer.open({
        type: 2,
        area: [width, height],
        fixed: false, //不固定
        title: title,
        content: content
    });
}
$(function () {
    JPlaceHolder.init();
    //底部触底
    var windowH = $(window).height();
    var headerH = $(".header").height();
    var footerH = $(".footer").height();
    $("#mid").css({"min-height": (windowH - headerH - footerH) + "px"});
    $("#midb").css({"min-height": (windowH - headerH - footerH - 300) + "px"});
    //底部微信二维码
    $('.btn_wechat i').hover(function () {
        $(".tipbox").stop(true).fadeIn();
    }, function () {
        $(".tipbox").fadeOut();
    })






    /*发送验证码前弹窗*/
    if ($("#getcode").length > 0) {
        $(document.body).append('<div class="modal-backdrop in" id="bgcode" style="z-index:7;display:none;"></div><div class="code-con"><div class="code-box"><div class="code-top">验证信息<div class="close-code">X</div></div><div class="code-body"><div class="alert-info"><p>提示:为了防止您的手机号被他人盗用，请您手动输入下方验证码</p></div><div class="code-verify"><span>验证码：</span><input type="text" name="vcode" id="ycode" maxlength="6"/><img src="/valicode" class="code-yzm" height="32" /><button type="button" class="code-btn btn" >确定</button></div></div></div></div>');
    }
    $(".close-code").click(function () {
        codebox.hide();
    })
    $('.code-yzm,.logincode').click(function (e) {
        var src = $(this).attr("src").split("?")[0] + "?r=" + Math.random();
        $(this).attr('src', src);
    });

    $(".code-yzm,.logincode").hover(function () {
        layer.tips('点击刷新验证码', $(this), {
            tips: [1, '#f08519'],
            time: 0
        });
    }, function () {
        layer.closeAll('tips');
    })

    /*注册*/
    $("#regbtn").click(function () {
        if (!isPhone($("#username").val())) {
            layer.msg('请输入正确的手机号', function () {
            });
            $("#username").select();
            return;
        }
        if ($("#phonecode").val() == '' || $("#phonecode").val().length < 5) {
            layer.msg('请输入正确的手机验证码', function () {
            });
            $("#phonecode").select();
            return;
        }
        if ($("#password").val().length < 6) {
            layer.msg('密码不能小于6位', function () {
            });
            $("#password").focus();
            return;
        }
        if ($("#password2").val().length < 6) {
            layer.msg('密码不能小于6位', function () {
            });
            $("#password2").focus();
            return;
        }
        if ($("#password2").val() != $("#password").val()) {
            layer.msg('两次密码输入不一致', function () {
            });
            return;
        }
        if (!$('#isread').is(':checked')) {
            layer.msg('请阅读并同意注册协议', function () {
            });
            return;
        }
        var _this = $(this);
        var data = $(this).parent().serialize(), url = $(this).parent().attr('action');
        $(this).attr('disabled', true).text('注册中...');
        ajax.submit(url, data, function (d) {
         if(d.success==true){
             layer.msg('注册成功，请登录！',{icon:1},function(){
                 window.location.href = "/login";
             });
         }else{
             layer.msg(d.resultMsg, function () {
             });
             _this.removeAttr('disabled').text('立即注册');
         }
        }, function (e) {
            layer.msg('网络繁忙请稍后再试', function () {
            });
            _this.removeAttr('disabled').text('立即注册');
            return;
        });
    })


    /*登录*/

    $("#loginbtn").click(function () {
        if (!isPhone($("#username").val())) {
            layer.msg('请输入正确的手机号', function () {
            });
            $("#username").select();
            return;
        }
        if ($("#password").val().length < 6) {
            layer.msg('密码不能小于6位', function () {
            });
            $("#password").focus();
            return;
        }
        if ($("#showcode").css('display') == 'block' && $("#ycode").val().length < 5) {
            layer.msg('请输入正确验证码', function () {
            });
            $("#ycode").select();
            return;
        }
        var _this = $(this);
        var data = $(this).parent().serialize(), url = $(this).parent().attr('action');
        _this.attr('disabled', true).text('登录中...');

        ajax.submit(url, data, function (d) {
            var od=$.parseJSON(d);
            if (od.success) {
                layer.msg('登录成功，正在加载页面，请稍等！',{icon:1,time:1000},function(){
                    window.location.href = "/user/user_info";
                });
            } else {
                switch (od.error) {
                    case "error3":
                        layer.msg('输入的密码不正确');
                        break;
                    case "no user found":
                        layer.msg('输入的用户名不存在');
                        break;
                    case "error1":
                        layer.msg('输入的验证码不正确');
                        break;
                    case "locked":
                        layer.msg('用户被锁定，请联系管理员');
                        break;
                    default:
                        layer.msg('登录出现错误，稍后再试');
                }
                if (od.errornum > 2) {
                    $("#showcode").css('display', 'block');
                    $("#ycode").attr("disabled", false)
                }
            }

            // $("#showcode").css('display','block'); 密码错误一定次数调用左边显示验证码
            _this.removeAttr('disabled').text('立即登录');
        }, function (e) {
            layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('立即登录');
            return;
        });
    })

    /*忘记密码*/
    $("#forgetbtn").click(function(){
        if(!isPhone($("#username").val())){
            layer.msg('请输入正确的手机号',function(){});
            $("#username").select();
            return;
        }
        if($("#phonecode").val()==''||$("#phonecode").val().length<6){
            layer.msg('请输入正确的手机验证码',function(){});
            $("#phonecode").select();
            return;
        }
        if($("#password").val().length<6){
            layer.msg('密码不能小于6位',function(){});
            $("#password").focus();
            return;
        }
        if($("#password2").val().length<6){
            layer.msg('密码不能小于6位',function(){});
            $("#password2").focus();
            return;
        }
        if($("#password2").val()!=$("#password").val()){
            layer.msg('两次密码输入不一致',function(){});
            return;
        }
        var _this = $(this);
        var data=$(this).parent().serialize(),url=$(this).parent().attr('action');
        $(this).attr('disabled',true).text('重置中...');

        ajax.submit(url,data,function(d){
           if(d.success==true){
               layer.msg('找回成功，请登录！',{icon:1},function(){
                   window.location.href="/login"
               });
           }else{
               layer.msg(d.resultMsg,function(){});
           }
            _this.removeAttr('disabled').text('重置密码');
        },function(e){
            layer.msg('网络繁忙请稍后再试',function(){});
            _this.removeAttr('disabled').text('重置密码');return;
        });
    })


    /*获取验证码*/
    $("#getcode").click(function () {
        if ($('#getcode').attr('unclick')) return;
        if (!isPhone($("#username").val()) && $(this).attr('ckold') != 1) {
            layer.msg('请输入正确的手机号', function () {
            });
            $("#username").select();
            return;
        }
        codebox.show();
        return;
    })

    $(".code-btn").hover(function () {
        if ($("#ycode").val() == '' || $("#ycode").val().length < 6) {
            layer.tips('请输入正确的手机验证码', $(this), {
                tips: [1, '#f08519'],
                time: 0
            });
            $("#ycode").focus();
            return;
        }
    }, function () {
        layer.closeAll('tips');
    })

    $(".code-btn").click(function () {
        var inputycode=$("#ycode").val();
        if (inputycode == '' || inputycode < 6) {
            layer.msg('请输入6位图形验证码', function () {
            });
            $("#ycode").focus();
            return;
        }
        codebox.hide();
        var _this = $('#getcode');
        var data = {phone: $("#username").val(), ycode: inputycode},
            url = '/validPhone';
        $('#getcode').attr('unclick', true).text('获取中...');
        ajax.submit(url, data, function (d) {
            if(d.success==true){
                layer.msg('验证码已发送到您手机');
                CountDown.Run(_this);
            }else{
                layer.msg(d.resultMsg);
                _this.removeAttr('disabled unclick').text('立即获取');
            }
        }, function (e) {
            layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled unclick').text('立即获取');
            return;
        });
    })

    /*修改绑定手机第一步*/
    $("#ckphone").click(function () {
        if ($("#phonecode").val().length < 6) {
            layer.msg('请输入正确的手机验证码', {offset: '193px'}, function () {
            });
            $("#phonecode").select();
            return;
        }
        var _this = $(this);
        var data = $('#safeform').serialize(), url = $('#safeform').attr('action');
        _this.attr('disabled', true).text('提交中...');
        ajax.submit(url, data, function (d) {
          if(d.success==true){
            location.href = "user_safe_editphone2.html";
          }else{
              parent.layer.msg(d.resultMsg);
              _this.removeAttr('disabled').text('下一步');
          }
        }, function (e) {
            parent.layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('下一步');
            return;
        });
    })


    /*修改绑定手机第二步*/
    $("#editphone").click(function () {
        if (!isPhone($("#username").val())) {
            layer.msg('请输入正确的手机号', {offset: '193px'}, function () {
            });
            $("#username").select();
            return;
        }

        if ($("#phonecode").val().length < 6) {
            layer.msg('请输入正确的手机验证码', {offset: '193px'}, function () {
            });
            $("#phonecode").select();
            return;
        }
        var _this = $(this);
        var data = $('#safeform').serialize(), url = $('#safeform').attr('action');
        _this.attr('disabled', true).text('修改中...');
        ajax.submit(url, data, function (d) {
            if(d.success==true){
                parent.layer.msg('修改成功',{icon:1},function () {
                    top.location.reload();
                });
            }else{
                parent.layer.msg(d.resultMsg);
                _this.removeAttr('disabled').text('下一步');
            }
        }, function (e) {
            parent.layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('立即修改');
            return;
        });
    })


    /*实名认证*/
    $("#authent").click(function () {
        var reg = /^[\u4E00-\u9FA5]+$/;
        if ($("#truename").val().length < 2 || !reg.exec($("#truename").val())) {
            layer.msg('请输入正确的真实姓名', {offset: '220px'}, function () {
            });
            $("#truename").select();
            return;
        }
        var checkFlag = new clsIDCard($("#idno").val());
        if (!checkFlag.IsValid()) {
            layer.msg('身份证号码格式错误', {offset: '220px'}, function () {
            });
            $("#idno").select();
            return;
        }
        var _this = $(this);
        var data = $('#safeform').serialize(), url = $('#safeform').attr('action');
        _this.attr('disabled', true).text('提交中...');
        ajax.submit(url, data, function (d) {
            if(d.success==true){
                parent.layer.msg('认证成功',{icon:1},function () {
                    top.location.reload();
                });
            }else{
                parent.layer.msg(d.resultMsg);
                _this.removeAttr('disabled').text('立即认证');
            }

        }, function (e) {
            parent.layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('立即认证');
            return;
        });
    })


    /*设置/修改提现密码*/
    $("#setwithdrawpass").click(function () {

        if ($("#phonecode").val() == '' || $("#phonecode").val().length < 6) {
            layer.msg('请输入正确的手机验证码', {offset: '310px'}, function () {
            });
            $("#phonecode").select();
            return;
        }
        if ($("#withdrawpass").val().length < 6) {
            layer.msg('提现密码不能小于6位', {offset: '310px'}, function () {
            });
            $("#withdrawpass").focus();
            return;
        }
        if ($("#withdrawpass2").val().length < 6) {
            layer.msg('提现密码不能小于6位', {offset: '310px'}, function () {
            });
            $("#withdrawpass2").focus();
            return;
        }
        if ($("#withdrawpass2").val() != $("#withdrawpass").val()) {
            layer.msg('两次提现密码输入不一致', {offset: '310px'}, function () {
            });
            return;
        }

        var _this = $(this);
        var data = $('#safeform').serialize(), url = $('#safeform').attr('action');
        _this.attr('disabled', true).text('提交中...');
        ajax.submit(url, data, function (d) {
            if(d.success==true){
                parent.layer.msg('提现密码设置成功',{icon:1},function () {
                    top.location.reload();
                });
            }else{
                parent.layer.msg(d.resultMsg);
                _this.removeAttr('disabled').text('立即设置');
            }

        }, function (e) {
            parent.layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('立即设置');
            return;
        });
    })


    /*修改登录密码*/
    $("#setloginpass").click(function () {

        if ($("#oldpass").val().length < 6) {
            layer.msg('旧密码不能小于6位', {offset: '240px'}, function () {
            });
            $("#oldpass").focus();
            return;
        }
        if ($("#newpass").val().length < 6) {
            layer.msg('新密码不能小于6位', {offset: '240px'}, function () {
            });
            $("#newpass").focus();
            return;
        }
        if ($("#newpass2").val().length < 6) {
            layer.msg('新密码不能小于6位', {offset: '240px'}, function () {
            });
            $("#newpass2").focus();
            return;
        }
        if ($("#newpass2").val() != $("#newpass").val()) {
            layer.msg('两次新密码输入不一致', {offset: '240px'}, function () {
            });
            return;
        }

        var _this = $(this);
        var data = $('#safeform').serialize(), url = $('#safeform').attr('action');
        _this.attr('disabled', true).text('修改中...');
        ajax.submit(url, data, function (d) {
            if(d.success==true){
                parent.layer.msg('登录密码修改成功',{icon:1},function () {
                    top.location.reload();
                });
            }else{
                parent.layer.msg(d.resultMsg);
                _this.removeAttr('disabled').text('立即修改');
            }
        }, function (e) {
            parent.layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('立即修改');
            return;
        });
    })
    /*修改信管家密码*/
    $("#setxgjpass").click(function () {

        if ($("#newpass").val().length < 6) {
            layer.msg('新密码不能小于6位', {offset: '240px'}, function () {
            });
            $("#newpass").focus();
            return;
        }
        if ($("#newpass2").val().length < 6) {
            layer.msg('新密码不能小于6位', {offset: '240px'}, function () {
            });
            $("#newpass2").focus();
            return;
        }
        if ($("#newpass2").val() != $("#newpass").val()) {
            layer.msg('两次新密码输入不一致', {offset: '240px'}, function () {
            });
            return;
        }
        var _this = $(this);
        var data = $('#safeform').serialize(), url = $('#safeform').attr('action');
        _this.attr('disabled', true).text('修改中...');
        ajax.submit(url, data, function (d) {
            if(d.success==true){
                parent.layer.msg('登录密码修改成功',{icon:1},function () {
                    top.location.reload();
                });
            }else{
                parent.layer.msg(d.resultMsg);
                _this.removeAttr('disabled').text('立即修改');
            }
        }, function (e) {
            parent.layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('立即修改');
            return;
        });
    })

    /*绑定银行卡*/
    $("#setbankcard").click(function () {
        if ($("#bankname").val() == '') {
            layer.msg('请选择开户银行', {offset: '355px'}, function () {
            });
            $("#bankname").focus();
            return;
        }
        if (!luhmCheck(Trim($("#bankcard").val(), 'g'))) {
            layer.msg('请输入正确的银行卡号', {offset: '355px'}, function () {
            });
            $("#bankcard").focus();
            return;
        }

        if ($("#phonecode").val().length < 6) {
            layer.msg('请输入正确的手机验证码', {offset: '355px'}, function () {
            });
            $("#phonecode").select();
            return;
        }

        var _this = $(this);
        var data = $('#safeform').serialize(), url = $('#safeform').attr('action');
        _this.attr('disabled', true).text('提交中...');
        ajax.submit(url, data, function (d) {
            if(d.success==true){
                parent.layer.msg('银行卡绑定成功',{icon:1},function () {
                    top.location.reload();
                });
            }else{
                parent.layer.msg(d.resultMsg);
                _this.removeAttr('disabled').text('立即绑定');
            }
        }, function (e) {
            parent.layer.msg('网络繁忙请稍后再试');
            _this.removeAttr('disabled').text('立即绑定');
            return;
        });
    })


    $("#goback").click(function () {
        parent.layer.closeAll();
    })
    // 退出登录
    $(".logout_a").click(function () {
        $("#logout").submit();
    })
});
