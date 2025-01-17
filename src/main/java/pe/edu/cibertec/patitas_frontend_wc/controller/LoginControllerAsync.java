//PARTE 1
package pe.edu.cibertec.patitas_frontend_wc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.cibertec.patitas_frontend_wc.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_frontend_wc.viewmodel.LoginModel;
import reactor.core.publisher.Mono;

//SE COLOCA CONTROLLER PARA LOS CONTROLADORES
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginControllerAsync {

    @Autowired
    WebClient webClientAutenticacion;


    //GetMapping --> Cuando quiero acceder a una url y uso la url para acceder al Path
    //PostMapping -->Cuando envio datos de un formulario en una pagina html e internamente envio esos datos

    //TODO METODO ASYNCRONO UTILIZANDO WEBCLIENT SIEMPRE TIENE QUE DEVOLVER UN MONO
    @PostMapping("/autenticar-async")
    public Mono<LoginResponseDTO> autenticar(@RequestBody LoginRequestDTO loginRequestDTO){


        //VALIDAR CAMPOS DE ENTRADA
        //trim() -->valida que no haya esoacios en blancos al principio


        //VALIDAMOS SI TODO ESTA MAL SI ES NULO Y TIENE ESPACIOS EN BLANCO
        if ( loginRequestDTO.tipoDocumento() == null || loginRequestDTO.tipoDocumento().trim().length()==0 ||
                loginRequestDTO.numeroDocumento()==null || loginRequestDTO.numeroDocumento().trim().length()==0 ||
                loginRequestDTO.password()==null || loginRequestDTO.password().trim().length()==0)  {



            return Mono.just(new LoginResponseDTO("01","Error:Debe completar correctamente sus credenciales","",""));
        }


      try {
          //Consumir servicio backend de autenticacion



          return webClientAutenticacion.post().uri("/login")
                  .body(Mono.just(loginRequestDTO),LoginRequestDTO.class)
                  .retrieve()
                  .bodyToMono(LoginResponseDTO.class)
                  .flatMap(response -> {

                      if (response.codigo().equals("00")){
                          return Mono.just(new LoginResponseDTO("00","", response.nombreUsuario(), ""));

                      }else {
                          return Mono.just(new LoginResponseDTO("02","Error:Autenticacion Fallida", "", ""));


                      }

                  });





      }catch (Exception e){

          System.out.println(e.getMessage());

          return Mono.just(new LoginResponseDTO("99","Error:Autenticacion Fallida", "", ""));


      }






    }

















}
