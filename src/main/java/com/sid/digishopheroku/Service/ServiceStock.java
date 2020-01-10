package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.ProduitRepository;
import com.sid.digishopheroku.IDaoRepository.StockRepository;
import com.sid.digishopheroku.IDaoRepository.TransactionRepository;
import com.sid.digishopheroku.Metier.MetierStock;
import com.sid.digishopheroku.Model.Produit;
import com.sid.digishopheroku.Model.Stock;
import com.sid.digishopheroku.WebRestfull.Forms.NewStockForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
public class ServiceStock  implements MetierStock {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    TransactionRepository transactionRepository;


    @Override
    public Stock reserveStock(Produit produit, int quantite) {
        if (produit.getStock().getShoppingStock()<quantite)throw new RejectedExecutionException("la quantité de stock que vous voulez reserver est superieure à la quantité de stock actuel");
        produit.getStock().setShoppingStock(produit.getStock().getShoppingStock()-quantite);
        produit.getStock().setReservedStock(produit.getStock().getReservedStock()+quantite);
        produit.setQuantiteEnStock(produit.getStock().getShoppingStock());
        produitRepository.save(produit);

        stockRepository.save(produit.getStock());
//trace de l operation
       // Transaction transaction = new Transaction();
        //transaction.setStock(produit.getStock());
      //  transaction.setMotif("Reservation - Directe - de - Stock - de " + quantite +" de "+produit.getNomProduit());
      //  transaction.setDateOperation(new Date());
      //  transactionRepository.save(transaction);
        return produit.getStock();
    }

    @Override
    public Stock repartirNouveauStock(NewStockForm stockForm) {
        Stock stock = new Stock();
        if ( stockForm.getQuotaReservedStock() < 0
        || stockForm.getQuotaShoppingStock()<0)throw new RuntimeException(" Mauvaise repartition de stock !");

         final int CENTIEME = 1/100;

        stock.setTotalStock(stockForm.getTotalStock());
        stock.setDateExpiration(stockForm.getDateExpiration());
        stock.setDateManufacture(stockForm.getDateManufacture());
        stock.setShoppingStock(stockForm.getQuotaShoppingStock()*stockForm.getTotalStock()/100);
        stock.setReservedStock(stockForm.getQuotaReservedStock()*stockForm.getTotalStock()/100);
        stock.setStockAlarmLimit(stockForm.getStockAlarmLimit());
        stock.setStockName(stockForm.getStockName());

        stock.setDernierAjout(stockForm.getTotalStock());
        stockRepository.save(stock);
        return stock;
    }


    @Override
    public Stock addNewStock(Produit produit, Stock stock) {
        // stock.setProduit(produit);
         // stockRepository.save(stock);
      // produit.setStock(stock);

         stock.setTotalStock( produit.getStock().getTotalStock() + stock.getTotalStock());
          //produit.getStock().setStockName(stock.getStockName());
          stock.setReservedStock(produit.getStock().getReservedStock() + stock.getReservedStock());
          stock.setShoppingStock(produit.getStock().getShoppingStock()+ stock.getShoppingStock());
          //produit.getStock().setStockAlarmLimit(stock.getStockAlarmLimit());
          //produit.getStock().setDateManufacture(stock.getDateManufacture());
          //produit.getStock().setDateExpiration(stock.getDateExpiration());
        stock.setProduit(produit);
          stockRepository.save(stock);
        produit.setStock(stock);
        produit.setQuantiteEnStock(/*produit.getQuantiteEnStock() +*/ stock.getShoppingStock()); // + stock.getShoppingStock() raison de la multiplication de la valeur de stock
        produitRepository.save(produit);
        return stock;
    }

    @Override
    public Stock addNewStock(Produit produit,NewStockForm stockForm) {

        produit.getStock().setStockName(stockForm.getStockName());
        produit.getStock().setTotalStock(produit.getStock().getTotalStock() + stockForm.getTotalStock());
        produit.getStock().setShoppingStock(produit.getStock().getShoppingStock() + stockForm.getTotalStock());
        produit.getStock().setDernierAjout(stockForm.getTotalStock());
        produit.getStock().setDateExpiration(stockForm.getDateExpiration());
        produit.getStock().setDateManufacture(stockForm.getDateManufacture());


       // stock.setTotalStock(stock.getTotalStock()+ stockForm.getTotalStock());
        //stock.setDateExpiration(stockForm.getDateExpiration());
       // stock.setDateManufacture(stockForm.getDateManufacture());
       // stock.setStockName(stockForm.getStockName());
       // stock.setShoppingStock(stock.getShoppingStock() + stockForm.getTotalStock());
       // stock.setDernierAjout(stockForm.getTotalStock());

        stockRepository.save(produit.getStock());

        produit.setQuantiteEnStock(produit.getQuantiteEnStock() + stockForm.getTotalStock());
        produitRepository.save(produit);
        return produit.getStock();
    }

    @Override
    public Long calcul_product_validity(Produit produit) {
        Stock stock = produit.getStock();
        long validity ;
         validity = (stock.getDateExpiration().getTime()-(new Date()).getTime());
        return validity;
    }

    @Override
    public Long calcul_stock_duration(Produit produit) {
        Stock stock = produit.getStock();
        long duration ;
        duration=((new Date()).getTime()-stock.getTransactionList().get(stock.getTransactionList().size()-1).getDateOperation().getTime());
        return duration;
    }

    @Override
    public boolean stock_limit_checked(Produit produit) {
        if (produit.getStock().getStockAlarmLimit()*produit.getStock().getTotalStock()<produit.getStock().getShoppingStock()){
            return true;
        }else {
            return false;
        }

    }


    @Override
    public Stock sortieStock(Produit produit, int qty) {

        if (produit.getQuantiteEnStock()<=qty) throw new RuntimeException("La Quantité Entree est superieure à celle en stock");

        produit.getStock().setShoppingStock(produit.getStock().getShoppingStock() - qty);
        produit.getStock().setTotalStock(
                produit.getStock().getShoppingStock()+produit.getStock().getReservedStock()
        );
        stockRepository.save(produit.getStock());
        produit.setQuantiteEnStock(produit.getQuantiteEnStock() - qty);
        produitRepository.save(produit);
        return produit.getStock();
    }

    @Override
    public Stock retourProduit(Produit produit, int qte) {
        if (qte<=0) throw new RuntimeException("La quanité entrée n est pas correcte.");
        produit.getStock().setShoppingStock(produit.getStock().getShoppingStock()+qte);
        produit.getStock().setTotalStock(
                produit.getStock().getShoppingStock()+produit.getStock().getReservedStock()
        );
        stockRepository.save(produit.getStock());

        produit.setQuantiteEnStock(produit.getQuantiteEnStock()+qte);
        produitRepository.save(produit);
        return produit.getStock();
    }

    @Override
    public Stock updateStockInventaire(Produit produit, int difference) {
if (difference>0){
            produit.getStock().setShoppingStock(produit.getStock().getShoppingStock()+ Math.abs(difference));
        produit.getStock().setTotalStock(
                produit.getStock().getShoppingStock()+produit.getStock().getReservedStock()
        );

        stockRepository.save(produit.getStock());

        produit.setQuantiteEnStock(produit.getQuantiteEnStock()+Math.abs(difference));
        produitRepository.save(produit);

    }else {
    produit.getStock().setShoppingStock(produit.getStock().getShoppingStock()- Math.abs(difference));
    produit.getStock().setTotalStock(
            produit.getStock().getShoppingStock()-produit.getStock().getReservedStock()
    );

    stockRepository.save(produit.getStock());

    produit.setQuantiteEnStock(produit.getQuantiteEnStock()+Math.abs(difference));
    produitRepository.save(produit);

} return produit.getStock();
    }


    @Override
    public List<Stock> findByReservedStockGreaterThan(int zero) {
        return stockRepository.findByReservedStockGreaterThan(zero);
    }
}
