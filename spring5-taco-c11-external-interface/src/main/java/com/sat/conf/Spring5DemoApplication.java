package com.sat.conf;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.sat.bean.Ingredient;
import com.sat.bean.Ingredient.Type;
import com.sat.bean.Taco;
import com.sat.data.interfaces.IngredientRepository;
import com.sat.data.interfaces.TacoRepository;
//之所以要加scan是因为默认只扫描配置类所在的包，如果要扫描其他包的组件需要加@ComponentScan，或者直接将这个类放到类路径下也行
//ComponentScan是扫描指定包及子包下的@Component注解以及使用@Component注解的注解（{@code @Repository}, {@code @Service}, or {@code @Controller} 等）
@ComponentScan(basePackages = {"com.sat"})
//扫描 jpa repository
@EnableJpaRepositories(basePackages = "com.sat")
//扫描 jpa entity 注释
@EntityScan(basePackages = "com.sat")
//一般推荐打包为jar包，jar包需要一个主类，这个就是
@SpringBootApplication//表明这是个spring应用，并且具有启动自动扫描，自动配置，还有类似@Configuration的功能

//该配置类非常非常重要，这个出问题就起不来
public class Spring5DemoApplication {

	public static void main(String[] args) {
		//这会创建应用上下文，第一个参数为配置类，第二个参数为命令行参数
		SpringApplication.run(Spring5DemoApplication.class, args);
	}
	
	//jpa的时候初始化数据库数据，这样是最合理的
	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo,TacoRepository tacoRepo) {
		return new CommandLineRunner() {
		    @Override
		    public void run(String... args) throws Exception {
//		    	repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
//		        repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
//		        repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
//		        repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
//		        repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
//		        repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
//		        repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
//		        repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
//		        repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
//		        repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
		    	Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
		        Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
		        Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
		        Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
		        Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
		        Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
		        Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
		        Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
		        Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
		        Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
		        repo.save(flourTortilla);
		        repo.save(cornTortilla);
		        repo.save(groundBeef);
		        repo.save(carnitas);
		        repo.save(tomatoes);
		        repo.save(lettuce);
		        repo.save(cheddar);
		        repo.save(jack);
		        repo.save(salsa);
		        repo.save(sourCream);
		        
		        Taco taco1 = new Taco();
	            taco1.setName("Carnivore");
	            taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
	            tacoRepo.save(taco1);

	            Taco taco2 = new Taco();
	            taco2.setName("Bovine Bounty");
	            taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
	            tacoRepo.save(taco2);

	            Taco taco3 = new Taco();
	            taco3.setName("Veg-Out");
	            taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
	            tacoRepo.save(taco3);
		    }
		};
	}

}
