// Juan camilo Acevedo Sanchez          -          Reto5              -           G8;
package modelo;

import static com.sun.tools.javac.tree.TreeInfo.args;
import controlador.ControladorProducto;
import java.util.Optional;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import vista.ActualizarMenu;
import vista.InformeMenu;
import vista.VistaProducto;

@SpringBootApplication
public class Reto5Application {
        
    @Autowired
    RepositorioProducto repositorio;
	public static void main(String[] args) {
		//SpringApplication.run(Reto5Application.class, args);
	
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Reto5Application.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
        }
    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            
            final Log logger = LogFactory.getLog(getClass());
            ControladorProducto controlador = new ControladorProducto(repositorio, new VistaProducto(), new ActualizarMenu(), new InformeMenu());
            
            /* //pruebas clase
            //add
            producto Manzana = producto.crearProducto("Manzana", 5000, 25);
            producto Limones = producto.crearProducto("Limones", 2300, 15);
            producto Peras = producto.crearProducto("Peras", 2700, 33);
            producto Arandanos = producto.crearProducto("Arandanos", 9300, 5);
            producto Tomates = producto.crearProducto("Tomates", 2100, 42);
            producto Fresas = producto.crearProducto("Fresas", 4100, 3);
            producto Helado = producto.crearProducto("Helados", 4500, 41);
            producto Galletas = producto.crearProducto("Galletas", 500, 8);
            producto Chocolates = producto.crearProducto("Chocolates", 3500, 80);
            producto Jamon = producto.crearProducto("Jamon", 15000, 10);   
            //save
            repositorio.save(Manzana);
            repositorio.save(Limones);
            repositorio.save(Peras);
            repositorio.save(Arandanos);
            repositorio.save(Tomates);
            repositorio.save(Fresas);
            repositorio.save(Helado);
            repositorio.save(Galletas);
            repositorio.save(Chocolates);
            repositorio.save(Jamon);
            */
            
            //read
           // List<producto> resultado = (List<producto>) repositorio.findAll();
           // logger.info(resultado);
            
            //search
            //producto resultado = repositorio.findById(1L).get();
            //logger.info(resultado);
            //update
           // producto resultado = repositorio.findById(1).get();
           // resultado.setNombre("Manzana");
           // repositorio.save(resultado);
            
            //delete
           // repositorio.deleteById(1L);
            
        };
    }

}
