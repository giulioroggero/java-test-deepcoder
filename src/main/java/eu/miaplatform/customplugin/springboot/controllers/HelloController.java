/*
 * Copyright 2020 Mia srl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.miaplatform.customplugin.springboot.controllers;

import eu.miaplatform.customplugin.springboot.CPStatus;
import eu.miaplatform.customplugin.springboot.CPStatusBody;
import eu.miaplatform.customplugin.springboot.models.Hello;
import eu.miaplatform.customplugin.springboot.CPController;
import eu.miaplatform.customplugin.springboot.CPRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "helloController")
public class HelloController extends CPController {

  @GetMapping("/hello")
  @ApiOperation(value = "Say hello")
  @ResponseBody
  public Hello sayHello(@ApiIgnore @ModelAttribute(CP_REQUEST) CPRequest cpRequest) {

    cpRequest.getHeadersPropagator().getHeaders().forEach(header ->
      logger.info("headerName: " + header.getName() + " - headerValue: " + header.getValue())
    );
    logger.info("Ciao");
    return new Hello("Ciao");
  }

  @Override
  public ResponseEntity readinessHandler(CPRequest cpRequest) {
    return customPluginService.addHandler(cpRequest, cpReq -> {
      CPStatusBody st = new CPStatusBody();
      st.setStatus(CPStatusBody.OK);
      return CPStatus.statusOk(st);
    });
  }

  @Override
  public ResponseEntity healthinessHandler(CPRequest cpRequest) {
    return customPluginService.addHandler(cpRequest, cpReq -> {
      CPStatusBody st = new CPStatusBody();
      st.setStatus(CPStatusBody.OK);
      return CPStatus.statusOk(st);
    });
  }

}