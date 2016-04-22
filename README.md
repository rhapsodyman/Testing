# Testing

**AssertJ**  
* http://joel-costigliola.github.io/assertj/

**Hamcrest matchers**    
* http://vk.com/away.php?to=https%3A%2F%2Fcode.google.com%2Fp%2Fhamcrest%2Fwiki%2FTutorial  
* http://vk.com/away.php?to=http%3A%2F%2Fhamcrest.org%2FJavaHamcrest%2Fjavadoc%2F1.3%2Forg%2Fhamcrest%2FMatchers.html  

**XmlUnit, JsonUnit**    
* https://github.com/xmlunit/xmlunit  
* https://github.com/lukas-krecan/JsonUnit  

**REST Assured**  
* https://github.com/jayway/rest-assured  
* http://www.jayway.com/?s=restassured  


http://www.guigarage.com/javafx-training-tutorials/
https://dzone.com/storage/assets/439403-rc219-javafx.pdf
http://code.makery.ch/library/javafx-8-tutorial/part1/


http://s000.tinyupload.com/?file_id=08355813971966643926

<properties>
		<jackson-2-version>2.7.0</jackson-2-version>
		<jackson-1-version>1.9.13</jackson-1-version>
	</properties>

	<dependencies>
		<!-- the core, which includes Streaming API, shared low-level abstractions 
			(but NOT data-binding) -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>${jackson-2-version}</version>
		</dependency>

		<!-- Just the annotations; use this dependency if you want to attach annotations 
			to classes without connecting them to the code. -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-annotations</artifactId>
			<version>${jackson-2-version}</version>
		</dependency>

		<!-- databinding; ObjectMapper, JsonNode and related classes are here -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>${jackson-2-version}</version>
		</dependency>
		
		
		<dependency>
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-mapper-asl</artifactId>
            <version>${jackson-1-version}</version>
        </dependency>
        <dependency>
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-core-asl</artifactId>
            <version>${jackson-1-version}</version>
        </dependency>



http://www.baeldung.com/jackson-serialize-enums



public static void main(String[] args) throws IOException {
		/*
		 * Collection files = FileUtils.listFiles(new File("D:\\abc"), new
		 * RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
		 * 
		 * System.out.println(files.toString()); for (Object file : files) {
		 * File fileObj = (File) file;
		 * System.out.println(fileObj.getCanonicalPath());
		 * System.out.println(fileObj.getName()); }
		 */

		List<String> files = printFnames("D:\\abc", new ArrayList<String>());

		for (String string : files) {
			System.out.println(string);

		}

	}

	public static List<String> printFnames(String sDir, List<String> files) {
		File[] faFiles = new File(sDir).listFiles();
		for (File file : faFiles) {

			if (file.isDirectory()) {
				printFnames(file.getAbsolutePath(), files);
			} else {
				if (file.getName().matches("^(.*?)")) {
					files.add(file.getAbsolutePath());
				}
			}
		}
		return files;
	}
