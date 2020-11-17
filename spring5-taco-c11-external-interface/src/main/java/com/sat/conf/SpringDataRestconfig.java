package com.sat.conf;

import java.util.Set;

import javax.persistence.Entity;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class SpringDataRestconfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		//使得Ingredient的id也暴露到rest中
//		config.exposeIdsFor(Ingredient.class);
//		config.exposeIdsFor(Taco.class);
		
		//某个包中的类全暴露id
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		final Set<BeanDefinition> beans = provider.findCandidateComponents("com.sat.bean");
		for (final BeanDefinition bean : beans) {
  	      	try {
  	      		config.exposeIdsFor(Class.forName(bean.getBeanClassName()));
  	      	} catch (final ClassNotFoundException e) {
  	      		// Can't throw ClassNotFoundException due to the method signature. Need to cast it
  	      		throw new IllegalStateException("Failed to expose `id` field due to", e);
  	      	}
		}

	}

	
	
}
