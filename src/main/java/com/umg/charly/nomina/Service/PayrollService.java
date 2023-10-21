package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.*;
import com.umg.charly.nomina.Repository.*;
import com.umg.charly.nomina.Tools.KeepAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("v1")
@CrossOrigin
public class PayrollService {

    @Autowired
    PayrollHeaderRepository payrollHeaderRepository;
    @Autowired
    PayrollPeriodRepository payrollPeriodRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PayrollDetailsRepository payrollDetailsRepository;


    Double ingreso = 0.0;
    Double egreso = 0.0;
    Double total = 0.0;
    Long id = 0L;

    //vars
    String okU = "Se actualiza correctamente";
    String okC = "Se creo correctamente";
    String failsC = "Hubo un problema al crear";
    String delete = "El registro fue eliminado exitosamente";
    String deleteE = "El registro tiene mas dependencias no puede ser borrado";
    String sesionFail = "Sesion no valida";
    HashMap<String, String> response = new HashMap<>();
    Double igss = 0.0483;
    Double IsrExonerante = 48000.00;
    Double limiteMedio = 300000.00;
    Double limiteMayor = 300000.01;
    Double isrMinimo = 0.05;
    Double isrMaximo = 0.07;
    Double anio = 12.00;
    Double valorFijo = 15000.00;
    Date primerDia = null;
    Date ultimoDia = null;


    @GetMapping(path = "/payrollHeaders")
    private List<PayrollHeader> payrollHeaders() {
        return payrollHeaderRepository.findAll();
    }

    //Servicios para periodo planilla
    @GetMapping(path = "/payrollPeriod")
    private List<PayrollPeriod> payrollPeriodLis() {
        return payrollPeriodRepository.findAll();
    }
    @PostMapping(path = "/createPayrollPeriod")
    private HashMap<String, String> createPayrollPeriod(@RequestBody PayrollPeriod payrollPeriod) {
        if (new KeepAlive().validateSession(UserFind(payrollPeriod.getUserCreation()).getCurrentSession())) {
            List<PayrollPeriod> payrollPeriodExistList = payrollPeriodRepository.findAll();
            boolean payrollPeriodAlreadyExist = false;

            for (PayrollPeriod payrollPeriodExist : payrollPeriodExistList) {
                if (payrollPeriodExist.getIdPK().getYear() == payrollPeriod.getIdPK().getYear() &&
                        payrollPeriodExist.getIdPK().getMonth() == payrollPeriod.getIdPK().getMonth()) {
                    payrollPeriodAlreadyExist = true;
                    break;
                }
            }
            if (payrollPeriodAlreadyExist) {
                response.put("code", "1");
                response.put("message", failsC);
                return response;
            } else {
                payrollPeriod.setCreationDate(new Date());
                payrollPeriodRepository.save(payrollPeriod);
                response.put("code", "0");
                response.put("message", okC);
                return response;
            }
        } else {
            response.put("code", "999");
            response.put("message", sesionFail);
            return response;
        }
    }
    @PutMapping(path = "/updatePayrollPeriod")
    private HashMap<String, String> updatePayrollPeriod(@RequestBody PayrollPeriod payrollPeriod) {
        try {
            if (new KeepAlive().validateSession(UserFind(payrollPeriod.getUserModification()).getCurrentSession())) {
                payrollPeriod.setModificationDate(new Date());
                payrollPeriodRepository.save(payrollPeriod);
                response.put("code", "0");
                response.put("message", okU);
                return response;
            } else {
                response.put("code", "1");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "999");
            response.put("message", sesionFail);
            return response;
        }
    }
    @DeleteMapping(path = "/deletePayrollPeriod/{anio}/{mes}/{user}")
    private HashMap<String, String> deletePayrollPeriod(@PathVariable Long anio, @PathVariable Long mes, @PathVariable String user) {
        try {
            if (new KeepAlive().validateSession(UserFind(user).getCurrentSession())) {
                PayrollPeriodPK payrollPeriodPK = new PayrollPeriodPK();
                payrollPeriodPK.setYear(anio);
                payrollPeriodPK.setMonth(mes);

                payrollPeriodRepository.deleteById(payrollPeriodPK);
                response.put("code", "0");
                response.put("message", delete);
                return response;
            } else {
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", deleteE);
            return response;

        }

    }


    //Servicios para periodo planilla
    @GetMapping(path = "/payrollHeader")
    private List<PayrollHeader> payrollHeaderList() {
        return payrollHeaderRepository.findAll();
    }
    //funcion de retorno de datos
    private User UserFind(String user) {
        return userRepository.findByIdUser(user);
    }
    private int idEmployee() {
        return payrollPeriodRepository.findAll().size();
    }


    //servicios de calculo
    @GetMapping(path = "PayrollCalc/{year}/{month}/{user}")
    public List<PayrollDetails> calculos(@PathVariable String year, @PathVariable String month, @PathVariable String user) {

        id = 0L;
        long yearN = Long.parseLong(year);
        long monthN = Long.parseLong(month);

        ParyollPeriodoCreate(yearN, monthN, user);
        for (Employee employee : employeeRepository.findAll()) {

            if(employee.getIdStatusEmployee() == 1){
                PayrollDetails(employee, yearN, monthN, user);
            }
        }
        return payrollDetailsRepository.findAll();
    }
    @DeleteMapping(path = "/deletePayroll/{year}/{month}")
    private HashMap<String, String> deletePayroll(@PathVariable long year, @PathVariable long month) {


        List<PayrollDetails> payrollDetailsList = payrollDetailsRepository.findAll();
        System.out.println("borrando detalle de planilla");
        for (PayrollDetails p : payrollDetailsList
        ) {
            if (p.getYear() == year && p.getMonth() == month) {
                payrollDetailsRepository.delete(p);
            }
        }

        List<PayrollHeader> payrollHeaderList = payrollHeaderRepository.findAll();
        System.out.println("borrando encabezado de planilla");
        for (PayrollHeader p : payrollHeaderList
        ) {
            if (p.getIdPK().getYear() == year && p.getIdPK().getMonth() == month) {
                payrollHeaderRepository.delete(p);
            }
        }

        List<PayrollPeriod> payrollPeriodList = payrollPeriodRepository.findAll();
        System.out.println("borrando periodo de planilla");
        for (PayrollPeriod p : payrollPeriodList
        ) {
            if (p.getIdPK().getMonth() == month && p.getIdPK().getYear() == year) {
                System.out.println("elimina year " + p.getIdPK().getYear() + " month " + p.getIdPK().getMonth());
                payrollPeriodRepository.delete(p);
            }
        }
        response.put("code", "0");
        response.put("message", delete);
        return response;
    }

    //metodos

    private void PayrollDetails(Employee employee, long year, long month, String user) {
        try {

            PayrollHeaderCreate(year, month, user);
            PayrollDetails payrollDetailsData = new PayrollDetails();
            id++;
            payrollDetailsData.setIdPayrollDetails(id);
            payrollDetailsData.setYear(year);
            payrollDetailsData.setMonth(month);
            payrollDetailsData.setIdEmployee(employee.getIdEmployee());
            payrollDetailsData.setDateOfHire(employee.getDateOfHire());
            payrollDetailsData.setIdPosition(employee.getIdPosition());
            payrollDetailsData.setIdStatusEmployee(employee.getIdStatusEmployee());
            payrollDetailsData.setBaseSalaryIncome(employee.getBaseSalaryIncome());
            payrollDetailsData.setBonusIncomeDecree(employee.getBonusIncomeDecree());
            payrollDetailsData.setIncomeOther(employee.getIncomeOther());
            payrollDetailsData.setIgss(Igss(employee.getBaseSalaryIncome()));
            payrollDetailsData.setIsr(Isr(employee.getBaseSalaryIncome()));
            ingreso = Suma(employee.getBaseSalaryIncome(), employee.getBonusIncomeDecree(), employee.getIncomeOther());
            egreso = Resta(employee.getBaseSalaryIncome(), employee.getNoShowDiscount());
            total = TotalIngresos(ingreso, egreso);
            payrollDetailsData.setNetSalary(total);
            payrollDetailsData.setNoShowDiscount(employee.getNoShowDiscount());
            payrollDetailsData.setCreationDate(new Date());
            payrollDetailsData.setUserCreation(user);
            payrollDetailsRepository.save(payrollDetailsData);
            PayrollHeaderUpdate(year, month, user);

        } catch (Exception e) {
            System.out.println(e.getCause() + " Mensaje -> " + e.getMessage());
        }

    }
    private void PayrollHeaderCreate(long year, long month, String user) {
        double ingreso = 0.00;
        double egreso = 0.00;

        PayrollHeader payrollHeader = new PayrollHeader();
        payrollHeader.setTotalIncome(ingreso);
        payrollHeader.setTotalDiscounts(egreso);
        PayrollHeaderPK pk = new PayrollHeaderPK();
        pk.setMonth(month);
        pk.setYear(year);
        payrollHeader.setIdPK(pk);
        payrollHeader.setSalary(ingreso - egreso);
        payrollHeader.setDateProcess(new Date());
        payrollHeader.setCreationDate(new Date());
        payrollHeader.setUserCreation(user);
        payrollHeaderRepository.save(payrollHeader);
    }
    private void PayrollHeaderUpdate(long year, long month, String user) {


        List<PayrollDetails> payrollDetails = payrollDetailsRepository.findAll();
        List<PayrollHeader> payrollHeaderFind = payrollHeaderRepository.findAll();
        PayrollHeader payrollHeader = new PayrollHeader();

        for (PayrollHeader p : payrollHeaderFind
        ) {
            if (p.getIdPK().getYear() == year && p.getIdPK().getMonth() == month) {
                payrollHeader = p;
            }
        }

        double ingreso = 0.00;
        double egreso = 0.00;

        for (PayrollDetails pd : payrollDetails
        ) {
            ingreso += Suma(pd.getBaseSalaryIncome(), pd.getBonusIncomeDecree(), pd.getIncomeOther());
            egreso += Resta(pd.getBaseSalaryIncome(), pd.getNoShowDiscount());
        }

        payrollHeader.setTotalIncome(ingreso);
        payrollHeader.setTotalDiscounts(egreso);
        payrollHeader.setSalary(ingreso - egreso);
        payrollHeader.setDateProcess(new Date());
        payrollHeader.setModificationDate(new Date());
        payrollHeader.setUserModification(user);
        payrollHeaderRepository.save(payrollHeader);

    }
    private void ParyollPeriodoCreate(long year, long month, String user) {

        PayrollPeriod payrollPeriod = new PayrollPeriod();
        PayrollPeriodPK pk = new PayrollPeriodPK();

        payrollPeriod.setCreationDate(new Date());
        payrollPeriod.setUserCreation(user);
        pk.setMonth(month);
        pk.setYear(year);
        payrollPeriod.setIdPK(pk);

        DateCalc((int) year, (int) month);
        payrollPeriod.setStartDate(primerDia);
        payrollPeriod.setEndDate(ultimoDia);

        payrollPeriodRepository.save(payrollPeriod);
    }
    private void DateCalc(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // Meses en Calendar van de 0 a 11
        cal.set(Calendar.DAY_OF_MONTH, 1);  // Primer día del mes
        primerDia = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Último día del mes
        ultimoDia = cal.getTime();
    }

    //calculos
    public Double Igss(double num) {
        return num * igss;
    }
    public Double Isr(double num) {
        double valorAnual = num * anio;
        double igssAnual = Igss(num) * anio;
        num = valorAnual - igssAnual;
        if (num < IsrExonerante) {
            return 0.00;
        } else if (num <= limiteMedio) {
            num = ((num - IsrExonerante) * isrMinimo) / anio;
        } else if (num >= limiteMayor) {
            num = ((num - IsrExonerante) * isrMaximo + valorFijo) / anio;
        }
        return num;
    }
    private Double Suma(double base, double bono, double extra) {
        return ingreso = base + bono + extra;
    }
    private Double Resta(double sueldo, double desc) {
        return egreso = Igss(sueldo) + Isr(sueldo) + desc;
    }
    private Double TotalIngresos(double ingreso, double egreso) {
        return total = ingreso - egreso;
    }


    @GetMapping(path = "/llenaEmployee")
    private void llenaEmpleado() {
        int y = 49800;
        Employee employee = new Employee();
        for (int x = 0; x < y; x++) {
            long id = employeeRepository.findAll().size();
            id++;
            employee.setIdEmployee(id);
            employee.setIdPerson(1L);
            employee.setIdLocation(1L);
            employee.setIdPosition(1L);
            employee.setIdStatusEmployee(1L);
            employee.setBaseSalaryIncome(25000.00);
            employee.setBonusIncomeDecree(250.00);
            employee.setIgss(Igss(25000));
            employee.setIsr(Isr(25000));
            employee.setIncomeOther(500.00);
            employee.setNoShowDiscount(0.00);
            employee.setCreationDate(new Date());
            employee.setDateOfHire(new Date());
            employee.setUserCreation("cristian");
            employeeRepository.save(employee);
        }

    }

    @GetMapping(path="/actualizaEmployee")
    private void actualiza(){
        Employee employee = new Employee();
        List<Employee> employeeList = employeeRepository.findAll();

        Double salario = 0.0;
        Double igss = 0.0;
        Double isr = 0.0;

        for (Employee employeeNew:employeeList
             ) {
        salario = employeeNew.getBaseSalaryIncome();
        igss = new PayrollService().Igss(salario);
        isr = new PayrollService().Isr(salario);
        employeeNew.setIgss(igss);
        employeeNew.setIsr(isr);
        employeeRepository.save(employeeNew);
        }

    }



}
