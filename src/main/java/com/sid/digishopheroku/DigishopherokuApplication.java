package com.sid.digishopheroku;

import com.sid.digishopheroku.Metier.MetierAccount;
import com.sid.digishopheroku.Metier.MetierCommande;
import com.sid.digishopheroku.Service.LireFichierExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class   DigishopherokuApplication implements CommandLineRunner {
	@Autowired
	private MetierAccount metierAccount;
	@Autowired
	LireFichierExcel lireFichierExcel;

@Autowired
MetierCommande metierCommande;

	public static void main(String[] args) {
		SpringApplication.run(DigishopherokuApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBcp(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		//AppUser user = new AppUser();
		//user.setEmailVerificationStatus(false);
		//user.
		//metierAccount.saveuser(new AppUser("user","1234","prosper@gmail.com","6",null));
/*
	Date date;
		date = new Date();
		metierAccount.saveuser(new AppUser("Prosper","1234","prosper@gmail.com","6",null));
		metierAccount.saveuser(new AppUser("Generique","1234","","0000",null));
		metierAccount.saverole(new AppRole("ADMIN"));
		metierAccount.saverole(new AppRole("USER"));
		metierAccount.saverole(new AppRole("VENDEUR"));
		metierAccount.saverole(new AppRole("PROPRIETAIRE"));

		metierAccount.addRoleToUser("6","USER");
		metierAccount.addRoleToUser("6","ADMIN");
		metierAccount.addRoleToUser("6","VENDEUR");
		metierAccount.addRoleToUser("6","PROPRIETAIRE");
		//metierAccount.addRoleToUser("admin","USER");
		metierAccount.addRoleToUser("0000","USER");
		System.out.println("nous somme le "+date);
		System.out.println("nous somme le "+date.toInstant());
		//1. Convert Date -> Instant
		Instant instant = date.toInstant();
		System.out.println("instant : " + instant); //Zone : UTC+0


		String current = System.getProperty("user.dir");
		System.out.println("Current working directory in Java : " + current);
		File file = new File(current+"/ecom/product/");
		File file1 = new File(current+"/ecom/shop/");
		if (file.isDirectory()) {
			System.out.println("Le dossiers est supprimé!");

				File[] file2=file.listFiles();
				for (File f: file2){
					f.delete();
				}
				file.delete();


			System.out.println("Le dossiers vient d'être creé !");
			System.out.println(file.mkdirs());
			System.out.println(file1.mkdirs());

		} else {
			if (file.mkdirs()) {
				System.out.println("dossiers crées");
				//System.out.println(file.getAbsolutePath());
			} else {
				System.out.println("Echec l'opération");
				//System.out.println(file.getAbsolutePath());
			}
		}

		if (file1.isDirectory()) {


				File[] file2=file1.listFiles();
				for (File f: file2){
					f.delete();
				}
				file1.delete();



			System.out.println(file1.mkdirs());
			System.out.println("Le dossiers vient d'être creer!");
			System.out.println(file.mkdirs());

		} else {
			if (file1.mkdirs()) {
				System.out.println("dossiers crées");
			//	System.out.println(file1.getAbsolutePath());
			} else {
				System.out.println("Echec l'opération");
				//System.out.println(file1.getAbsolutePath());
			}
		}

		lireFichierExcel.getExcel();




		//Commande c1 = metierCommande.Add_Commd_vendeur()
*/
	}





}
