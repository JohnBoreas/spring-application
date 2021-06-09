package com.boreas.spider.js;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * @Description: 执行js方法
 * @author xuhua.jiang
 * @date: 2018年9月14日
 */
public class ExecuteJs {

	public static void main(String[] args) {
		ExecuteJs js = new ExecuteJs();
		String dynamicScrip = "GJ5fmcyMpYwF5mI8rqb497p3h2XpK41eqZUyyYOWtsukje8mD2m2ayQdctYfWZ6w8rD8okDZ50vB";
		int rString = 3;
		try {
			js.executeJs(dynamicScrip, rString);
		} catch (Exception e) {
			
		}
	}

	public void executeJs(String dynamicScrip, int rString) throws FileNotFoundException, ScriptException, NoSuchMethodException {
		/* mimeType为传输的文件类型,如 application/javascript */
		/* 获取执行JavaScript的执行引擎 */
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		
		/* 为文件注入全局变量 */
		Bindings bindings = engine.createBindings();
		bindings.put("factor", 2);
		/* 设置绑定参数的作用域 */
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		System.out.println("当前输入的参数为: " + dynamicScrip + ":" + rString);
		/* 执行js文件代码 */
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		String path;
		if (url != null) {
			path = url.getPath();
			engine.eval(new FileReader(path + "spider" + "/h5.js"));
			/* 查看是否可以调用方法 */
			if (engine instanceof Invocable) {
				Invocable in = (Invocable) engine;
				in.invokeFunction("formula", dynamicScrip, rString);
				System.out.println("输出结果为" + in.invokeFunction("formula", dynamicScrip, rString));
			}
		}
	}
}
