/*
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~ accountability and the service delivery of the government  organizations.
  ~
  ~  Copyright (C) 2016  eGovernments Foundation
  ~
  ~  The updated version of eGov suite of products as by eGovernments Foundation
  ~  is available at http://www.egovernments.org
  ~
  ~  This program is free software: you can redistribute it and/or modify
  ~  it under the terms of the GNU General Public License as published by
  ~  the Free Software Foundation, either version 3 of the License, or
  ~  any later version.
  ~
  ~  This program is distributed in the hope that it will be useful,
  ~  but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  GNU General Public License for more details.
  ~
  ~  You should have received a copy of the GNU General Public License
  ~  along with this program. If not, see http://www.gnu.org/licenses/ or
  ~  http://www.gnu.org/licenses/gpl.html .
  ~
  ~  In addition to the terms of the GPL license to be adhered to in using this
  ~  program, the following additional terms are to be complied with:
  ~
  ~      1) All versions of this program, verbatim or modified must carry this
  ~         Legal Notice.
  ~
  ~      2) Any misrepresentation of the origin of the material is prohibited. It
  ~         is required that all modified versions of this material be marked in
  ~         reasonable ways as different from the original version.
  ~
  ~      3) This license does not grant any rights to any user of the program
  ~         with regards to rights under trademark law for use of the trade names
  ~         or trademarks of eGovernments Foundation.
  ~
  ~  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.

*/
package org.egov.ptis.web.controller.masters;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import org.egov.ptis.domain.model.MutationFeeDetails;
import org.egov.ptis.master.service.AddMutationFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/addmutationfee")
public class AddMutationFeeController {
    private static final String MUTATION_FORM = "add-mutation-fee-form";

    private final AddMutationFeeService addMutationFeeService;

    @Autowired
    public AddMutationFeeController(final AddMutationFeeService addMutationFeeService) {
        this.addMutationFeeService = addMutationFeeService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute final MutationFeeDetails mutationFeeDetails, final Model model) {
        return MUTATION_FORM;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute final MutationFeeDetails mutationFeeDetails,
            final BindingResult resultBinder,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final Model model) {

        if (addMutationFeeService.validateForHighLimitNull()) {
            resultBinder.reject("error.mutation.add.fail", "error.mutation.add.fail");
            return MUTATION_FORM;
        } else if (!addMutationFeeService.validateForMaxHighLimit(mutationFeeDetails.getLowLimit())) {
            final BigDecimal high = addMutationFeeService.getMaxHighLimit().add(BigDecimal.ONE);
            resultBinder.reject("error.mutation.fail", new String[] { high.toString() }, "error.mutation.fail");
            return MUTATION_FORM;
        } else {
            addMutationFeeService.createMutationFee(mutationFeeDetails);
            redirectAttributes.addFlashAttribute("message", "msg.addmutationfee.create.success");
            return "redirect:/addmutationfee/create";
        }
    }

}
