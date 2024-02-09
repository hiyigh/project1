package main;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
@SpringBootApplication
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter (
				type = FilterType.ASSIGNABLE_TYPE,
				value = {ContextInstanceDataAutoConfiguration.class, ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class})})
@RequiredArgsConstructor
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}

