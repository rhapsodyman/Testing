package simpletest;

import java.io.File;

import javax.xml.transform.Source;

import org.testng.annotations.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonListener;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DOMDifferenceEngine;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.DifferenceEngine;


public class xmlUnitTest {

	@Test
	public void tetsXML() {

		/*Source source = Input.fromFile(new File("./xmls/expected.xml")).build();
		XPathEngine xpath = new JAXPXPathEngine();
		String content = xpath.evaluate("/note/body/text()", source);*/

		Source expected = Input.fromFile(new File("./xmls/expected.xml")).build();
		Source actual = Input.fromFile(new File("./xmls/actual.xml")).build();

		DifferenceEngine diff = new DOMDifferenceEngine();
		
		/*diff.addDifferenceListener(new ComparisonListener() {
			public void comparisonPerformed(Comparison comparison, ComparisonResult outcome) {
				 Assert.fail("found a difference: " + comparison); 

				System.out.println("Found a difference: " + comparison);
			}
		});

		diff.addMatchListener(new ComparisonListener() {
			public void comparisonPerformed(Comparison comparison, ComparisonResult outcome) {
				System.out.println("Found a match: " + comparison.getControlDetails().getXPath());
			}
		});
		
		
		//diff.addMatchListener( (comparison, compRes)  ->  System.out.println("Found a match: " + comparison));
		
		diff.compare(expected, actual);*/
		
		
		Diff d = DiffBuilder.compare(expected).withTest(actual).build();   // we can use custom withComparisonFormatter 
	
		
		if (d.hasDifferences()) {
			Iterable<Difference> differences = d.getDifferences();
			for (Difference difference : differences) {
				System.out.println(difference);
			}
		}
	}
}
