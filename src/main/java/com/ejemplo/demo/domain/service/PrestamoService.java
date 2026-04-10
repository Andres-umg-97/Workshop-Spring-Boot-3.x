package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PrestamoService {

    public PrestamoResponse simular(BigDecimal monto, BigDecimal tasaAnual, int meses) {

        double r = tasaAnual.doubleValue() / 12 / 100;
        double P = monto.doubleValue();
        int n = meses;

        double cuota = P * (r * Math.pow(1 + r, n)) /
                (Math.pow(1 + r, n) - 1);

        BigDecimal cuotaMensual = BigDecimal.valueOf(cuota);
        BigDecimal total = cuotaMensual.multiply(BigDecimal.valueOf(n));
        BigDecimal interes = total.subtract(monto);

        return new PrestamoResponse(cuotaMensual, interes, total);
    }
}