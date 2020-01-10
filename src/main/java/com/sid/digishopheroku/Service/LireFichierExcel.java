package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.CategorieProduitRepository;
import com.sid.digishopheroku.Model.CategorieProduit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Service
public  class LireFichierExcel {
    @Autowired
    private CategorieProduitRepository categorieProduitRepository;
    XSSFWorkbook fichier;
    XSSFSheet feuille;

    public void getExcel(){
        FileInputStream fils;

        {
            try {
                fils = new FileInputStream(new File("Catalogue produits.xlsx"));

                fichier=new XSSFWorkbook(fils);
                int index=1;
                feuille=fichier.getSheetAt(0);
                Row row=feuille.getRow(index++);
                while (row!=null){
                    categorieProduitRepository.save(new CategorieProduit(row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue(),row.getCell(2).getStringCellValue()));
                    //System.out.println(+"\t"+row.getCell(1).getStringCellValue()+"\t"+row.getCell(2).getStringCellValue());
                    System.out.println();
                    row=feuille.getRow(index++);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
