package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.PayrollHeader;
import com.umg.charly.nomina.Entity.PayrollPeriod;
import com.umg.charly.nomina.Entity.PayrollPeriodPK;
import com.umg.charly.nomina.Entity.User;
import com.umg.charly.nomina.Repository.PayrollHeaderRepository;
import com.umg.charly.nomina.Repository.PayrollPeriodRepository;
import com.umg.charly.nomina.Repository.UserRepository;
import com.umg.charly.nomina.Tools.KeepAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //vars
    String okU = "Se actualiza correctamente";
    String okC = "Se creo correctamente";
    String failsU = "Hubo un problema al actualizar";
    String failsC = "Hubo un problema al crear";
    String delete = "El registro fue eliminado exitosamente";
    String delelteE = "El registro tiene mas dependencias no puede ser borrado";
    String sesionFail = "Sesion no valida";
    HashMap<String, String> response = new HashMap<>();
    Double Igssx = 0.0483;
    Double IsrExonerante = 48000.00;
    Double Cobro5 = 300000.00;
    Double Cobro7 = 300001.00;
    Double Isr5 = 0.05;
    Double Isr7 = 0.07;
    Double anio = 12.00;
    Double alto = 15000.00;
    String noCobro = "No se cobra ISR";

    @GetMapping(path = "/payrollHeaders")
    private List<PayrollHeader> payrollHeaders(){
        return payrollHeaderRepository.findAll();
    }


    //Servicios para periodo planilla
    @GetMapping(path = "/payrollPeriod")
    private List<PayrollPeriod> payrollPeriodLis(){
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
        }else{
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
            }else{
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
            }else{
                response.put("code", "999");
                response.put("message", sesionFail);
                return response;
            }
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }

    private User UserFind(String user) {
        return userRepository.findByIdUser(user);
    }

    private Double Igss(Double num) {
        num = num * Igssx;
        return num;
    }

    private Double Isr(Double num) {
        double year = num * anio;
        double igs = Igss(num);
        igs = igs * anio;
        num = year - igs;
        if(num < IsrExonerante){
            return 0.00;
        }else if(num > IsrExonerante && num <= Cobro5){
        num = year - num;
        num = num * Isr5;
        }else if(num >= Cobro7){
            num = year - num;
            num = num * Isr7;
            num = num + alto;
        }
        return num;
    }
}
