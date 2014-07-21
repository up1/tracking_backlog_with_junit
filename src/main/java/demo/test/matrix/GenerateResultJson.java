package demo.test.matrix;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class GenerateResultJson {

	List<BacklogModel> backlogList = new ArrayList<BacklogModel>();

	public GenerateResultJson(List<BacklogModel> backlogList) {
		this.backlogList = backlogList;
	}

	public void writeFile(String filename) {
		try {
			File file = new File(BacklogRunner.BACKLOG_RESULT_DIRECTORY);
			file.mkdir();
			PrintWriter out = new PrintWriter(new File(filename));
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, backlogList);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
