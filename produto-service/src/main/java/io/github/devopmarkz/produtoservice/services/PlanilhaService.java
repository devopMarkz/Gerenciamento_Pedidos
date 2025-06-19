package io.github.devopmarkz.produtoservice.services;

import io.github.devopmarkz.produtoservice.model.Produto;
import io.github.devopmarkz.produtoservice.repositories.ProdutoRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanilhaService {

    private final ProdutoRepository produtoRepository;

    public PlanilhaService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public byte[] gerarPlanilha(){
        List<Produto> produtos = gerarListaDeProdutos().stream().sorted().toList();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Relatório de produtos");

            Row cabecalho = sheet.createRow(0);
            String[] titulos = {"Id", "Nome", "Descrição", "Preço", "Quantidade"};
            for (int i = 0; i < titulos.length; i++) {
                cabecalho.createCell(i).setCellValue(titulos[i]);
            }

            int rowNum = 1;

            for (Produto produto : produtos) {
                Row produtoRow = sheet.createRow(rowNum++);
                produtoRow.createCell(0).setCellValue(produto.getId());
                produtoRow.createCell(1).setCellValue(produto.getNome());
                produtoRow.createCell(2).setCellValue(produto.getDescricao());
                produtoRow.createCell(3).setCellValue(produto.getPreco().doubleValue());
                produtoRow.createCell(4).setCellValue(produto.getQuantidade());
            }

            int lastRowNum = sheet.getLastRowNum();

            Double valorTotal = produtos.stream().map(produto -> produto.getPreco().doubleValue()).reduce(0.0, Double::sum);
            Integer quantiaTotalProdutos = produtos.stream().map(Produto::getQuantidade).reduce(0, Integer::sum);

            for (int i = 0; i < lastRowNum; i++) {
                Row resultados = sheet.createRow(lastRowNum + 1);
                resultados.createCell(3).setCellValue("Total: " + valorTotal);
                resultados.createCell(4).setCellValue("Produtos em estoque: " + quantiaTotalProdutos);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Produto> gerarListaDeProdutos() {
        return produtoRepository.findAll();
    }

}
