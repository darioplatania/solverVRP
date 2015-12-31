package main;

import jsprit.core.problem.constraint.SoftConstraint;

public class Balancing implements SoftConstraint {
	
	//leggere numero veicoli totali: VTOT
	//leggere numero customers totali: CTOT
	//BILANCIAMENTO: OGNI VEICOLO SERVE y = CTOT/VTOT clienti totali
	//verificare se l'aggiunta di un veicolo
	//dare dunque y routes ad ogni veicolo
	//verificare se l'aggiunta di un veicolo per una route aumenta il costo aumenta il costo o meno
	//tramite la .getCost(), se aumenta, evitare l'assegnazione
	
	//mix tra equo numero di clienti da servire e costo totale delle rotte del veicolo

}
