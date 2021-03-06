/*******************************************************************************
 * Copyright (C) 2014  Stefan Schroeder
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
/*******************************************************************************
 * Copyright (C) 2014  Stefan Schroeder
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jsprit.core.algorithm.recreate;

import jsprit.core.algorithm.recreate.InsertionData.NoInsertionFound;
import jsprit.core.problem.VehicleRoutingProblem;
import jsprit.core.problem.job.Job;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.util.NoiseMaker;
import main.OROoptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;




/**
 * Best insertion that insert the job where additional costs are minimal.
 *
 * @author stefan schroeder
 * 
 */
public final class BestInsertion extends AbstractInsertionStrategy{

	int job;
	
	private static Logger logger = LogManager.getLogger(BestInsertion.class);

	private JobInsertionCostsCalculator bestInsertionCostCalculator;

	private NoiseMaker noiseMaker = new NoiseMaker() {

		@Override
		public double makeNoise() {
			return 0;
		}

	};

	public BestInsertion(JobInsertionCostsCalculator jobInsertionCalculator, VehicleRoutingProblem vehicleRoutingProblem) {
		super(vehicleRoutingProblem);
		job = vehicleRoutingProblem.getJobs().size();
		bestInsertionCostCalculator = jobInsertionCalculator;
		logger.debug("initialise {}", this);
	}

	@Override
	public String toString() {
		return "[name=bestInsertion]";
	}
	
	public static int balancingJobs(int veh, int job) {
	    //if(veh == 0) return job;
		return Math.round(job / veh);
	}

	@Override
	public Collection<Job> insertUnassignedJobs(Collection<VehicleRoute> vehicleRoutes, Collection<Job> unassignedJobs) {
        List<Job> badJobs = new ArrayList<Job>(unassignedJobs.size());
        List<Job> unassignedJobList = new ArrayList<Job>(unassignedJobs);


        
        //System.out.println("JobPerVehicle: " + jobPerVehicle);
        
        Collections.shuffle(unassignedJobList, random);
		for(Job unassignedJob : unassignedJobList){			
			Insertion bestInsertion = null;
			double bestInsertionCost = Double.MAX_VALUE;
			if(vehicleRoutes.size()==OROoptions.nVeicoli && OROoptions.nVeicoli!=0 ){ // ------------- if then aggiunto

		        /*consider that can't have a division per 0 [IF in balancingJobs()] */				

				int job = 100; //replace with total solomon instances
		        int jobPerVehicle = balancingJobs(OROoptions.nVeicoli, job);

		        
				for(VehicleRoute vehicleRoute : vehicleRoutes){
					InsertionData iData = bestInsertionCostCalculator.getInsertionData(vehicleRoute, unassignedJob, NO_NEW_VEHICLE_YET, NO_NEW_DEPARTURE_TIME_YET, NO_NEW_DRIVER_YET, bestInsertionCost); 
					if(iData instanceof NoInsertionFound) continue;
					if(iData.getInsertionCost() < bestInsertionCost + noiseMaker.makeNoise()){
						
						if(jobPerVehicle < vehicleRoutes.size() ) {
							bestInsertion = new Insertion(vehicleRoute,iData);

							bestInsertionCost = iData.getInsertionCost();

						bestInsertionCost = iData.getInsertionCost();

						}
					}
				}
			}
			else if(OROoptions.nVeicoli==0){
				for (VehicleRoute vehicleRoute : vehicleRoutes) {
	                InsertionData iData = bestInsertionCostCalculator.getInsertionData(vehicleRoute, unassignedJob, NO_NEW_VEHICLE_YET, NO_NEW_DEPARTURE_TIME_YET, NO_NEW_DRIVER_YET, bestInsertionCost);
	                if (iData instanceof NoInsertionFound) {
	                    continue;
	                }
	                if (iData.getInsertionCost() < bestInsertionCost + noiseMaker.makeNoise()) {
	                    bestInsertion = new Insertion(vehicleRoute, iData);
	                    bestInsertionCost = iData.getInsertionCost();
		                /* HERE THERE ISN'T BALACING BECAUSE THIS PART REPRODUCES L1 */
	                }
	            }
	            VehicleRoute newRoute = VehicleRoute.emptyRoute();
	            InsertionData newIData = bestInsertionCostCalculator.getInsertionData(newRoute, unassignedJob, NO_NEW_VEHICLE_YET, NO_NEW_DEPARTURE_TIME_YET, NO_NEW_DRIVER_YET, bestInsertionCost);
	            if (!(newIData instanceof NoInsertionFound)) {
	                if (newIData.getInsertionCost() < bestInsertionCost + noiseMaker.makeNoise()) {
	                    bestInsertion = new Insertion(newRoute, newIData);
	                    vehicleRoutes.add(newRoute);
	                }
	            }
			}
			else{
					VehicleRoute newRoute = VehicleRoute.emptyRoute();
					InsertionData newIData = bestInsertionCostCalculator.getInsertionData(newRoute, unassignedJob, NO_NEW_VEHICLE_YET, NO_NEW_DEPARTURE_TIME_YET, NO_NEW_DRIVER_YET, bestInsertionCost);
					if(!(newIData instanceof NoInsertionFound)){
						if(newIData.getInsertionCost() < bestInsertionCost + noiseMaker.makeNoise()){
							bestInsertion = new Insertion(newRoute,newIData);
							vehicleRoutes.add(newRoute);
						}
					}
			}
			
			if(bestInsertion == null) badJobs.add(unassignedJob);
            else insertJob(unassignedJob, bestInsertion.getInsertionData(), bestInsertion.getRoute());
        }
		
        return badJobs;
	}

}
