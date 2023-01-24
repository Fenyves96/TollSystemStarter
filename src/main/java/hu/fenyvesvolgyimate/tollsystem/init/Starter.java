package hu.fenyvesvolgyimate.tollsystem.init;

import hu.fenyvesvolgyimate.tollsystem.client.VehicleRegisterClient;
import hu.fenyvesvolgyimate.tollsystem.VignetteLister;
import hu.fenyvesvolgyimate.tollsystem.VignetteListerAPI;
import hu.fenyvesvolgyimate.tollsystem.controller.TollSystemController;
import hu.fenyvesvolgyimate.tollsystem.dao.SqlLiteVignetteStorage;
import hu.fenyvesvolgyimate.tollsystem.dao.VignetteStorage;
import hu.fenyvesvolgyimate.tollsystem.presenter.VignettePresenterImpl;
import hu.fenyvesvolgyimate.tollsystem.view.TollSystemView;
import hu.fenyvesvolgyimate.vehicleregisterapp.entity.Vehicle;
import hu.fenyvesvolgyimate.vehicleregisterapp.interactor.VehicleReader;
import hu.fenyvesvolgyimate.vehicleregisterapp.interactor.VehicleRegister;

import hu.fenyvesvolgyimate.vehicleregisterapp.storage.FileVehicleRepository;
import hu.fenyvesvolgyimate.vehicleregisterapp.storage.VehicleRepository;

public class Starter {
    public static void main (String [] args){
        VehicleRepository vehicleRepository = new FileVehicleRepository();

        VehicleRegisterClient vehicleApiCaller = new VehicleRegisterClient();
        VehicleReader vehicleReader = new VehicleRegister(vehicleRepository, vehicleApiCaller);

        vehicleApiCaller.setVehicleReader(vehicleReader);
        VignetteStorage vignetteStorage = new SqlLiteVignetteStorage();
        VignettePresenterImpl vignettePresenter = new VignettePresenterImpl();
        VignetteListerAPI vignetteListerAPI = new VignetteLister(vignetteStorage, vehicleApiCaller, vignettePresenter);
        TollSystemController tollSystemController = new TollSystemController(vignetteListerAPI);
        TollSystemView tollSystemView = new TollSystemView(tollSystemController);
        vignettePresenter.setView(tollSystemView);
        tollSystemView.start();
    }
}
