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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.miaplatform.customplugin.springboot.CPController;
import eu.miaplatform.customplugin.springboot.CPRequest;
import eu.miaplatform.customplugin.springboot.CPStatus;
import eu.miaplatform.customplugin.springboot.CPStatusBody;
import eu.miaplatform.customplugin.springboot.models.Hello;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "helloController")
public class HelloController extends CPController {

  @GetMapping("/hello/{names}")
  @ApiOperation(value = "Say hello")
  @ResponseBody
  public Hello sayHello(@PathVariable final String names) {

    String salutation = ""; 

    String god = "Elena";
    String admin = "Giulio";
    String user1 = "Edo";
    String user2 = "Artu";

    String[] namesArray = names.split(",");

    List<String> people = findPeople(null, names);

    for (String name : namesArray) {
      if (name.equals(god)) {
        salutation += " super admin Elena! ";
      }

      if (name.equals(admin)) {
        salutation += " admin Giulio";
      }

      if (name.equals(user2)) {
        salutation += " user Artu! ";
      }

      if (name.equals(user1)) {
        salutation += " user Edo! ";
      }

    }

    return new Hello(salutation);
  }

  protected List<String> findPeople(Connection connection, String peopleList){
    List<String> res = new ArrayList<String>();

    try {
      Statement statement = connection.createStatement();
      String query = "SELECT * FROM users where name IN (" + peopleList + ")";
      ResultSet rs;
      rs = statement.executeQuery(query);
      
      while ( rs.next() ) {
          res.add(rs.getString("Lname"));
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return res;
  }

  @Override
  public ResponseEntity readinessHandler(final CPRequest cpRequest) {
    return customPluginService.addHandler(cpRequest, cpReq -> {
      final CPStatusBody st = new CPStatusBody();
      st.setStatus(CPStatusBody.OK);
      return CPStatus.statusOk(st);
    });
  }

  @Override
  public ResponseEntity healthinessHandler(final CPRequest cpRequest) {
    return customPluginService.addHandler(cpRequest, cpReq -> {
      final CPStatusBody st = new CPStatusBody();
      st.setStatus(CPStatusBody.OK);
      return CPStatus.statusOk(st);
    });
  }

}
