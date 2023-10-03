package com.umg.charly.nomina.Tools;

import com.umg.charly.nomina.Entity.Employee;
import com.umg.charly.nomina.Entity.PayrollDetails;
import com.umg.charly.nomina.Entity.PayrollPeriod;
import com.umg.charly.nomina.Repository.EmployeeRepository;
import com.umg.charly.nomina.Repository.PayrollDetailsRepository;
import com.umg.charly.nomina.Repository.PayrollPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1")
public class OperationsCalculations {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PayrollDetailsRepository payrollDetailsRepository;
    @Autowired
    PayrollPeriodRepository payrollPeriodRepository;

    Double ingreso = 0.0;
    Double egreso = 0.0;
    Double total = 0.0;
    Long id= 0L;



    public Double Suma(double base, double bono, double extra) {
        return ingreso = base + bono + extra;
    }


    public Double Resta(double igss, double isr, double desc) {
        return egreso = igss + isr + desc;
    }


    public Double TotalIngresos(double ingreso, double egreso) {
        return total = ingreso - egreso;
    }


    @GetMapping(path = "testPlayroll")
    public List<PayrollDetails> calculos() {
        List<PayrollPeriod> payrollPeriod = payrollPeriodRepository.findAll();
        PayrollPeriod data = payrollPeriod.get(0);
        long id = idEmployee();
        for (Employee employee : employeeRepository.findAll()) {
            grabaPlanillaDetalle(employee, data);
        }
        return payrollDetailsRepository.findAll();
    }

    public int idEmployee(){
        return payrollPeriodRepository.findAll().size();
    }


    public void grabaPlanillaDetalle(Employee employee, PayrollPeriod payrollPeriod) {
        try {
            PayrollDetails payrollDetailsData = new PayrollDetails();
            id++;
            payrollDetailsData.setIdPayrollDetails(id);
            payrollDetailsData.setYear(payrollPeriod.getIdPK().getYear());
            payrollDetailsData.setMonth(payrollPeriod.getIdPK().getMonth());
            payrollDetailsData.setIdEmployee(employee.getIdEmployee());
            payrollDetailsData.setDateOfHire(employee.getDateOfHire());
            payrollDetailsData.setIdPosition(employee.getIdPosition());
            payrollDetailsData.setIdStatusEmployee(employee.getIdStatusEmployee());
            payrollDetailsData.setBaseSalaryIncome(employee.getBaseSalaryIncome());
            payrollDetailsData.setBonusIncomeDecree(employee.getBonusIncomeDecree());
            payrollDetailsData.setIncomeOther(employee.getIncomeOther());
            payrollDetailsData.setIgss(employee.getIgss());
            payrollDetailsData.setIsr(employee.getIsr());
             ingreso = Suma(employee.getBaseSalaryIncome(),employee.getBonusIncomeDecree(),employee.getIncomeOther());
             egreso = Resta(employee.getIgss(),employee.getIsr(),employee.getNoShowDiscount());
             total = TotalIngresos(ingreso,egreso);
            payrollDetailsData.setNetSalary(total);
            payrollDetailsData.setNoShowDiscount(employee.getNoShowDiscount());
            payrollDetailsData.setCreationDate(new Date());
            payrollDetailsData.setUserCreation("temporal");
            payrollDetailsRepository.save(payrollDetailsData);
        } catch (Exception e) {
            System.out.println(e.getCause() + " Mensaje -> " + e.getMessage());
        }

    }

}
