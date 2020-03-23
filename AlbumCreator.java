import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AlbumCreator {

	private static String template = "<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"\n" + 
			"	<head>\n" + 
			"		<meta charset=\"utf-8\" />\n" + 
			"		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" + 
			"		<title>Gallery</title>\n" + 
			"		<meta content=\"width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0\" name=\"viewport\" />\n" + 
			"		<meta content=\"yes\" name=\"apple-mobile-web-app-capable\" />\n" + 
			"		<meta content=\"black\" name=\"apple-mobile-web-app-status-bar-style\" />\n" + 
			"		<meta content=\"telephone=no\" name=\"format-detection\" />\n" + 
			"		<link rel=\"icon\" type=\"image/png\" href=\"/icons/favico.png\" />\n" + 
			"		<script src=\"https://cdnjs.cloudflare.com/ajax/libs/vue/2.5.22/vue.min.js\"></script>\n" + 
			"		<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css\" integrity=\"sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS\" crossorigin=\"anonymous\">\n" + 
			"		<script src=\"https://unpkg.com/vue-lazyload/vue-lazyload.js\"></script>\n" + 
			"		<script src=\"https://cdn.jsdelivr.net/npm/v-img@latest/dist/v-img.min.js\"></script>\n" + 
			"		<style>\n" + 
			"			img {\n" + 
			"				width: 200px;\n" + 
			"				height: 200px;\n" + 
			"				object-fit: scale-down;\n" + 
			"			}\n" + 
			"\n" + 
			"			body {\n" + 
			"				margin-left: 30px;\n" + 
			"			}\n" + 
			"\n" + 
			"			* {\n" + 
			"				font-family: Arial, FreeSans, Roboto, Ubuntu !important;\n" + 
			"			}\n" + 
			"		</style>\n" + 
			"	</head>\n" + 
			"\n" + 
			"	<body>\n" + 
			"		<div id=\"vapp\">\n" + 
			"			<nav class=\"navbar fixed-top navbar-dark bg-dark\">\n" + 
			"				<a class=\"navbar-brand\" href=\"/\">Gallery</a>\n" + 
			"			</nav>\n" + 
			"			<div style=\"margin-top:70px;\">\n" + 
			"				<div class=\"row col-lg-12\" style=\"margin-bottom:20px\" v-if=\"directories.length > 0\">\n" + 
			"					<div class=\"row col-lg-12\">\n" + 
			"						<span style=\"font-size:18px;font-weight:400;color:#555\">Folders\n" + 
			"						</span>\n" + 
			"					</div>\n" + 
			"					<div class=\"row col-lg-12\">\n" + 
			"						<div v-for=\"dir of directories\" @click=\"openDir(dir)\" style=\"display: inline-block;margin:10px 10px 10px 0;cursor:pointer;\">\n" + 
			"							<div :title=\"dir\" style=\"width:200px;height:50px;border:1px solid #eee;font-size:13px;color:#444;padding-left:5px;margin:5px;border-radius:5px;display:table-cell;vertical-align:middle\">\n" + 
			"								<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" style=\"width:20px;height:20px;fill:#555;margin:0 auto;margin-right:5px;float:left\">\n" + 
			"									<path d=\"M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z\" />\n" + 
			"									<path d=\"M0 0h24v24H0z\" fill=\"none\" />\n" + 
			"								</svg>\n" + 
			"								<span style=\"display:inline-block;vertical-align:middle;font-size:15px;line-height:20px;color:#555;font-weight:500;max-width:175px;overflow:hidden;text-overflow:ellipsis;max-height:40px\">{{dir}}</span>\n" + 
			"							</div>\n" + 
			"						</div>\n" + 
			"					</div>\n" + 
			"				</div>\n" + 
			"				<div class=\"row col-lg-12\" style=\"margin-bottom:20px\" v-if=\"images.length > 0\">\n" + 
			"					<div class=\"row col-lg-12\" style=\"margin-bottom:10px\">\n" + 
			"						<span style=\"font-size:18px;font-weight:400;color:#555\">Images\n" + 
			"						</span>\n" + 
			"					</div>\n" + 
			"					<div class=\"row col-lg-12 d-flex justify-content-center\">\n" + 
			"						<img v-for=\"link of images\" v-lazy=\"link\" v-img=\"{group :  'gallery', src : link, sourceButton: 'true'}\">\n" + 
			"					</div>\n" + 
			"				</div>\n" + 
			"			</div>\n" + 
			"			<script>\n" + 
			"				Vue.use(VueLazyload)\n" + 
			"				new Vue({\n" + 
			"					el: '#vapp',\n" + 
			"					data: {\n" + 
			"						directories: [\n" + 
			"							%s\n" + 
			"						],\n" + 
			"						images: [\n" + 
			"							%s\n" + 
			"						]\n" + 
			"					},\n" + 
			"					methods: {\n" + 
			"						openDir(dir) {\n" + 
			"							var currentUrl = window.location.href;\n" + 
			"							if (currentUrl.endsWith(\"index.html\")) {\n" + 
			"								currentUrl = currentUrl.replace(\"index.html\", \"\");\n" + 
			"							}\n" + 
			"							window.location = currentUrl + dir + \"/\";\n" + 
			"						}\n" + 
			"					}\n" + 
			"				})\n" + 
			"\n" + 
			"			</script>\n" + 
			"	</body>\n" + 
			"\n" + 
			"</html>";

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File( System.getProperty("user.dir"));
		generatePage(file);
	}

	private static void generatePage(File parent) throws FileNotFoundException {
		List<String> dirsList = new ArrayList<>(1);
		List<String> imagesList = new ArrayList<>(1);
		for (File file : parent.listFiles()) {
			if (file.isDirectory()) {
				generatePage(file);
				dirsList.add("'" + file.getName() + "'");
			} else {
				if (isImage(file)) {
					imagesList.add("'" + file.getName() + "'");
				}
			}
		}
		String sDirsList = String.join(", ", dirsList);
		String sImagesList = String.join(", ", imagesList);
		String fileContent = String.format(template, sDirsList, sImagesList);
		String indexPath = parent.getAbsolutePath() + "/index.html";
		PrintWriter out = new PrintWriter(indexPath);
		out.print(fileContent);
		out.flush();
		out.close();
	}

	private static boolean isImage(File file) {
		String name = file.getName().toLowerCase().trim();
		if (name.endsWith(".jpg") || name.endsWith("png") || name.endsWith(".jpeg") || name.endsWith("svg")) {
			return true;
		}
		return false;
	}

}

