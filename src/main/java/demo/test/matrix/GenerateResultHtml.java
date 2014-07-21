package demo.test.matrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

public class GenerateResultHtml {

	TreeMap<String, List<BacklogModel>> backlogMap = new TreeMap<String, List<BacklogModel>>();

	public void writeFile() {
		try {
			String jsonPath = "backlog_result" + File.separator;
			File jsonFile = new File(jsonPath);
			File[] jsonFiles = jsonFile.listFiles();
			for (File json : jsonFiles) {
				if (json.getName().endsWith(".html")) {
					continue;
				}
				ObjectMapper mapper = new ObjectMapper();
				String jsonString = readFile(json);
				List<BacklogModel> backlogModelList = mapper.readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, BacklogModel.class));
				for (BacklogModel backlogModel : backlogModelList) {
					String key = backlogModel.getName();// +
														// backlogModel.getClassName();
					if (backlogMap.containsKey(key)) {
						List<BacklogModel> currentBacklogList = backlogMap.get(key);
						currentBacklogList.add(backlogModel);
					} else {
						List<BacklogModel> newBacklogList = new ArrayList<BacklogModel>();
						newBacklogList.add(backlogModel);
						backlogMap.put(key, newBacklogList);
					}
				}
			}

			String filename = "backlog_result" + File.separator + "result.html";
			PrintWriter out = new PrintWriter(new File(filename));
			StringBuilder html = new StringBuilder();
			html.append(addStylesheet());
			html.append("<table class='table'>");
			html.append("<thead><tr><th>Backlog Number</th><th>Class Name</th><th>Method Name</th></tr></thead>");
			for (String key : backlogMap.keySet()) {
				List<BacklogModel> backlogList = backlogMap.get(key);
				boolean flag = true;
				for (BacklogModel backlogModel : backlogList) {
					html.append("<tr>");
					if (flag && backlogList.size() > 1) {
						html.append("<td rowspan='").append(backlogList.size()).append("' >").append(backlogModel.getName()).append("</td>");
						flag = false;
					} else if (flag) {
						html.append("<td>").append(backlogModel.getName()).append("</td>");
					}
					html.append("<td>").append(backlogModel.getClassName()).append("</td>");
					html.append("<td>").append(backlogModel.getMethodName()).append("</td>");
					html.append("</tr>");
				}
			}
			html.append("</table>");
			out.print(html);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("finally")
	private String readFile(File file) {
		StringBuffer outputBuffer = new StringBuffer();
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				outputBuffer.append(sCurrentLine);
			}
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return outputBuffer.toString();
		}
	}

	private Object addStylesheet() {
		return "<link rel='stylesheet' href='../css/bootstrap.min.css'>";
	}

}
