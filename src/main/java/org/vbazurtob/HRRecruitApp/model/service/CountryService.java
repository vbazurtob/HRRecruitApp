/*
 * Personal License Agreement
 * Copyright Notice
 *
 * © 2025 Voltaire Bazurto Blacio. All rights reserved.
 * License Terms
 *
 *     Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
 *
 *     Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
 *
 *     Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
 *
 *     Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
 *
 *     Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.
 *
 * By accessing this portfolio, you agree to abide by these terms.
 */

package org.vbazurtob.HRRecruitApp.model.service;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.vbazurtob.HRRecruitApp.model.Country;

@Service
public class CountryService {

  private List<Country> listCountries;

  public CountryService() {}

  @PostConstruct
  public void init() throws IOException {

    try (

    // Replaced code for reading the country files inside a packaged .war (or jar). InpuStream in
    // required to be used
    InputStream in = getClass().getResourceAsStream("/list_countries.csv");
    InputStreamReader reader = new InputStreamReader(in)) {
      this.listCountries =
          new CsvToBeanBuilder<Country>(reader).withType(Country.class).build().parse();
    } catch (Exception e) {
      e.printStackTrace();
      Country  us =  new Country();
      us.setName("United States");
      us.setCode("US");
        this.listCountries = List.of(us);
    }
  }

  public List<Country> getListCountries() {
    return listCountries;
  }

  public void setListCountries(List<Country> listCountries) {
    this.listCountries = listCountries;
  }
}
