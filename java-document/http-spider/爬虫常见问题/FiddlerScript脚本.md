拦截指定链接

```js
static function OnDone(oSession: Session) {
        var httpResponseCode = oSession.oResponse.headers.HTTPResponseCode;
        var localPath:String = gs_localPath;
        var url = oSession.fullUrl;
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day= date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (day>= 1 && day<= 9) {
            day = "0" + day;
        }
        var currentdate = year + seperator1 + month + seperator1 + day;
//https://img.alicdn.com/bao/uploaded/i1/250801815/TB2rHf3XH1J.eBjSszcXXbFzVXa_!!250801815.jpg_250x250.jpg
        if ((httpResponseCode==200) && localPath 
        && (url.Contains("rest/n/user/search"))) {
            var requestInfo = oSession.GetRequestBodyAsString();
            //var pattern= /"[Uu]in"\s*:\s*"?(?:xuin=)?(\d+)"?/;           
            //var array = pattern.exec(requestInfo);
            var date1 = new Date();
            var curMills = date1.getTime();
            var path = localPath + "/userList/" + curMills + ".txt";
            //FiddlerObject.log('data storage had saved: ' + bodyUrl);
            oSession.SaveResponseBody(path);                                
        }
        if (localPath && url.Contains("mtop.alimama.moon.account.info/2.0")) {
            var responseHeader = oSession.ResponseHeaders; 
            var date1 = new Date();
            var curMills = date1.getTime();
            var path = localPath + "/alimama/" + curMills + ".txt"; 
            //oSession.SaveResponseBody(path);
            oSession.SaveResponse(path,false);
            oSession.SaveSession(path,false);
        }
    }
```

设置本地JS，打开页面，强制引用本地JS

```js
static function OnBeforeResponse(oSession: Session) {
    //20210116
    if(oSession.url.Contains("js/commission.3b841c8e.js")){
      var filename = "D:/workspace/automationLogin/src/main/resources/dcc_js/commission.3b841c8e_20210115.js";
      var jsContent = System.IO.File.ReadAllText(filename);
      FiddlerObject.log("----------commission----------" + jsContent.Length);
      oSession.utilSetResponseBody(jsContent);
      oSession.utilDecodeResponse();
    }else if(oSession.url.Contains("js/searchshopuser.9935bcaf.js")){
      var filename = "D:/workspace/automationLogin/src/main/resources/dcc_js/searchshopuser.9935bcaf_20210115.js";
      var jsContent = System.IO.File.ReadAllText(filename);
      FiddlerObject.log("----------searchshopuser----------" + jsContent.Length);
      oSession.utilSetResponseBody(jsContent);
      oSession.utilDecodeResponse();
}
```

