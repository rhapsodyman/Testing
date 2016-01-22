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
* 

package second;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassB {
	public static void main(String[] args) {
		String input = "djm";
		String[] collection = { "django_migrations.py", "django_admin_log.py", "main_generator.py", "migrations.py",
				"api_user.doc", "user_group.doc", "accounts.txt" };

		
		String patt = String.join(".*", input.split("(?!^)"));

		Pattern pattern = Pattern.compile(patt);
		
		for (int i = 0; i < collection.length; i++) {
			Matcher matcher = pattern.matcher(collection[i]);
			// check all occurance
			while (matcher.find()) {
				System.out.print("Start index: " + matcher.start());
				System.out.print(" End index: " + matcher.end() + " ");
				System.out.println(matcher.group());
			}
			
		}

		
	}
}


