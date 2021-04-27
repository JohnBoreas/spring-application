拦截指定链接

```js

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

