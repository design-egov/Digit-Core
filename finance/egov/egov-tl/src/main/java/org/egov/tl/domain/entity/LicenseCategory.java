/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) <2015>  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *  	1) All versions of this program, verbatim or modified must carry this
 *  	   Legal Notice.
 *
 *  	2) Any misrepresentation of the origin of the material is prohibited. It
 *  	   is required that all modified versions of this material be marked in
 *  	   reasonable ways as different from the original version.
 *
 *  	3) This license does not grant any rights to any user of the program
 *  	   with regards to rights under trademark law for use of the trade names
 *  	   or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.tl.domain.entity;

import org.egov.infra.persistence.validator.annotation.Required;
import org.egov.infstr.models.BaseModel;
import org.hibernate.validator.constraints.Length;

/**
 * The Class TradeCategory.
 */
public class LicenseCategory extends BaseModel {
    private static final long serialVersionUID = 1L;
    @Required(message = "tradelic.master.tradecategoryname.null")
    @Length(max = 256, message = "tradelic.masters.tradecategoryname.length")
    private String name;

    @Required(message = "tradelic.master.tradecategorycode.null")
    @Length(max = 32, message = "tradelic.masters.tradecategorycode.length")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("LicenseCategory={");
        str.append("serialVersionUID=").append(serialVersionUID);
        str.append("name=").append(name == null ? "null" : name.toString());
        str.append("code=").append(code == null ? "null" : code.toString());
        str.append("}");
        return str.toString();
    }

}
