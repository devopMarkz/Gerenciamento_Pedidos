package io.github.devopMarkz.cliente_service.services;

import io.github.devopMarkz.cliente_service.model.Cliente;
import io.github.devopMarkz.cliente_service.repositories.ClienteRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PlanilhaService {

    private final ClienteRepository clienteRepository;

    public PlanilhaService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public byte[] gerarPlanilha(){
        List<Cliente> clientes = listarClientes();

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Relatório de Clientes");

            Row cabecalho = sheet.createRow(0);
            String[] titulos = {"Id", "Nome", "CPF", "Fidelizado", "Total de Compras"};

            for (int i = 0; i < titulos.length; i++) {
                cabecalho.createCell(i).setCellValue(titulos[i]);
            }

            int rowNum = 1;

            for (Cliente cliente : clientes) {
                Row clienteRow = sheet.createRow(rowNum++);
                clienteRow.createCell(0).setCellValue(cliente.getId());
                clienteRow.createCell(1).setCellValue(cliente.getNome());
                clienteRow.createCell(2).setCellValue(cliente.getCpf());
                clienteRow.createCell(3).setCellValue(cliente.getFidelizado());
                clienteRow.createCell(4).setCellValue(cliente.getQuantidadeDeCompras());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Cliente> listarClientes(){
        return clienteRepository.findAll().stream().sorted().toList();
    }

}
