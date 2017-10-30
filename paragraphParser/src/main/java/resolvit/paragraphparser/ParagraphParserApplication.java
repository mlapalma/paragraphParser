package resolvit.paragraphparser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import resolvit.paragraphparser.contextconfigurations.ParagraphParserContextConfiguration;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class ParagraphParserApplication {

    public static void main(String[] args){

        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(ParagraphParserContextConfiguration.class)
                .run(args);

        ParagraphHelper paragraphHelper = context.getBean(ParagraphHelper.class);

        System.out.println(paragraphHelper.processParagraphFromConfiguration());
    }

}
