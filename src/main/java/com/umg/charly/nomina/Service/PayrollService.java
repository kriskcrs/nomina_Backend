package com.umg.charly.nomina.Service;

import com.umg.charly.nomina.Entity.PayrollHeader;
import com.umg.charly.nomina.Entity.PayrollPeriod;
import com.umg.charly.nomina.Entity.PayrollPeriodPK;
import com.umg.charly.nomina.Repository.PayrollHeaderRepository;
import com.umg.charly.nomina.Repository.PayrollPeriodRepository;
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

    //vars
    String okU = "Se actualiza correctamente";
    String okC = "Se creo correctamente";
    String failsU = "Hubo un problema al actualizar";
    String failsC = "Hubo un problema al crear";
    String delete = "El registro fue eliminado exitosamente";
    String delelteE = "El registro tiene mas dependencias no puede ser borrado";
    HashMap<String, String> response = new HashMap<>();

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
        List<PayrollPeriod> payrollPeriodExistList = payrollPeriodRepository.findAll();
        boolean payrollPeriodAlreadyExist = false;

        for (PayrollPeriod payrollPeriodExist : payrollPeriodExistList) {
            if (payrollPeriodExist.getIdPK().getAnio() == payrollPeriod.getIdPK().getAnio() &&
                    payrollPeriodExist.getIdPK().getMes() == payrollPeriod.getIdPK().getMes()) {
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
    }

    @PutMapping(path = "/updatePayrollPeriod")
    private HashMap<String, String> updatePayrollPeriod(@RequestBody PayrollPeriod payrollPeriod) {
        try {
            payrollPeriod.setModificationDate(new Date());
            payrollPeriodRepository.save(payrollPeriod);
            response.put("code", "0");
            response.put("message", okU);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", failsU);
            return response;
        }
    }

    @DeleteMapping(path = "/deletePayrollPeriod/{anio}/{mes}")
    private HashMap<String, String> deletePayrollPeriod(@PathVariable Long anio, @PathVariable Long mes) {
        try {
            PayrollPeriodPK payrollPeriodPK = new PayrollPeriodPK();
            payrollPeriodPK.setAnio(anio);
            payrollPeriodPK.setMes(mes);

            payrollPeriodRepository.deleteById(payrollPeriodPK);
            response.put("code", "0");
            response.put("message", delete);
            return response;
        } catch (Exception e) {
            response.put("code", "1");
            response.put("message", delelteE);
            return response;

        }

    }


}
