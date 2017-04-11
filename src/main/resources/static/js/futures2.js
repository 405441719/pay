var futures = {
    /*
     * hf_DXF:'美元指数期货'
     * hf_SF:'瑞士法郎'
     * hf_CD:'加元'
     * hf_JY:'日元'
     * hf_BP:'英镑'
     * hf_EC:'欧元'
     * rt_hkHSI:'恒生指数'
     * hf_NAS:'纳指期货'
     * hf_ES:'标普期货'
     * hf_DJS:'道指期货'
     * hf_CL:'原油'
     * hf_NG:'天然气'
     * hf_OIL:'布伦特原油'
     * hf_GC:'黄金'
     * hf_SI:'白银'
     * hf_HG:'铜'
     * hf_XAU:'伦敦金'
     * hf_XAG:'伦敦银'
     * hf_XPT:'伦敦铂金'
     * hf_S:'黄豆'
     * hf_W:'小麦'
     * hf_C:'玉米'
     * hf_BO:'黄豆油'
     * hf_SM:'黄豆粉'
     * hf_TRB:'日本橡胶'
     * hf_LHC:'瘦猪肉'
     */
    fname : 'hf_DXF,hf_SF,hf_CD,hf_JY,hf_BP,hf_EC,hf_NAS,hf_ES,hf_DJS,hf_CL,hf_NG,hf_OIL,hf_GC,hf_SI,hf_HG,hf_XAU,hf_XAG,hf_XPT,hf_S,hf_W,hf_C,hf_BO,hf_SM,hf_TRB,hf_LHC',
    url : 'http://hq.sinajs.cn/list=',
    loadJs : function(sid,jsurl,callback){
            var nodeHead = document.getElementsByTagName('head')[0];
            var nodeScript = null;
            $("#"+sid).remove();
            if(document.getElementById(sid) == null){
                nodeScript = document.createElement('script');
                nodeScript.setAttribute('type', 'text/javascript');
                nodeScript.setAttribute('src', jsurl);
                nodeScript.setAttribute('id',sid);
                if (callback != null) {
                    nodeScript.onload = nodeScript.onreadystatechange = function(){
                        if (nodeScript.ready) {
                            return false;
                        }
                        if (!nodeScript.readyState || nodeScript.readyState == "loaded" || nodeScript.readyState == 'complete') {
                            nodeScript.ready = true;
                            callback();
                        }
                    }
                }
                nodeHead.appendChild(nodeScript);
            } else {
                if(callback != null){
                    callback();
                }
            }
        },
    getrst : function(fname){
        if(fname==''||fname==null||fname==undefined) fname=this.fname;
        var new_url = this.url+fname,_ts=this;
        fname = fname.split(',');
        this.loadJs('getsinadata',new_url,function(){
            $(".f_box").css('background','none');
            var ndat=  new Array();
            for (var i=0;i<fname.length;i++){
                ndat[fname[i]]=_ts.fmat(fname[i]);
            }
            for(var i in ndat){
                if(i=='rt_hkHSI'){
                     var hkhsi = ndat[i][6]+','+ndat[i][8]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]+','+ndat[i][6]
                }
                if(ndat[i][1]>=0){
                    $("#"+i).attr('class','up');
                    ndat[i][1]='+'+ndat[i][1];
                }else{
                    $("#"+i).attr('class','down');
                }
                if($("#"+i).html()!='' && ndat[i][0]!=$("#"+i+' td').eq(1).html()) _ts.setiv($("#"+i));
                var swing = _ts.toDecimal4(ndat[i][0]-ndat[i][7]);
                if(swing>0)swing=swing+'%';
                $("#"+i).html('<td>'+ndat[i][13]+'</td><td>'+ndat[i][0]+'</td><td>'+swing+'</td><td>'+ndat[i][1]+'%</td><td>'+ndat[i][8]+'</td><td>'+ndat[i][4]+'</td><td>'+ndat[i][5]+'</td><td>'+ndat[i][7]+'</td><td>'+ndat[i][2]+'</td><td>'+ndat[i][3]+'</td><td>'+ndat[i][6]+'</td>');
            }
        });
    },
    setiv : function(obj){
        var time=1;
        var objsetiv = setInterval(function(){
            if(time<=5){
                if(obj.hasClass('hv')) obj.removeClass('hv');else obj.addClass('hv');
                time++;
            }else{
                if(obj.hasClass('hv')) obj.removeClass('hv');
                clearInterval(objsetiv);
            }
        },600)
    },
    fmat : function(fname){
        /* 
         * 0:当前价，1:增幅百分比，2:买入价，3:卖出价，4:最高价，5:最低价，6:时间，
         * 7:昨日结算价，8:开盘价，9:持仓量，10:买量，11:卖量，12:年月日，13:期货名称
         */
        var dat = window['hq_str_'+fname];
        if(fname!=''&&fname!=null&&fname!=false&&fname!=undefined){
            dat = dat.split(',');
        }
        return dat;
    },
    toDecimal4 : function(x){
      var f = parseFloat(x);    
      if (isNaN(f)) {
          return false;
      }
      var f = Math.round(x*10000)/10000;    
      var s = f.toString();
      var rs = s.indexOf('.');
      if (rs < 0) {
          rs = s.length;    
          s += '.';    
      }
      while (s.length <= rs + 4) {
          s += '0';    
      }    
      return s;    
    } 
}
$(function(){
    // futures.getrst();
    setInterval(function(){
        futures.getrst();
    },5000)
    $(".f_type span").click(function(){
        if($(this).attr('class')!='cur'){
            $('.f_type .cur').removeClass('cur');
            $(this).addClass('cur');
            var boxobj=$("#"+$(this).attr('id')+'_con');
            $(".f_box .show").removeClass('show');
            boxobj.addClass('show');
        }
    })

})