    package com.geektrust.backend.AppConfig;

    import com.geektrust.backend.Utils.DiscountCalculator;
    import com.geektrust.backend.Utils.RechargeProcessor;
    import com.geektrust.backend.commands.*;
    import com.geektrust.backend.data.MetroStationDataLoader;
    import com.geektrust.backend.data.PassengerFaresLoader;
    import com.geektrust.backend.entities.MetroCard;
    import com.geektrust.backend.entities.MetroStation;
    import com.geektrust.backend.repositories.*;
    import com.geektrust.backend.service.*;

    public class ApplicationConfig {

        private final int PERCENT_CONVERSION_FACTOR = 100;
        private final long SERVICE_FEE_PERCENTAGE = 2;
        private final long RETURN_JOURNEY_DISCOUNT_PERCENTAGE = 50;

        public ApplicationConfig() {
            this.loadPassengerFares();
            this.loadMetroStationsData();
        }
        IMetroCardRepository<MetroCard, String> metroCardRepository = new MetroCardRepository();
        IPassengerJourneyRepository passengerJourneyRepository = new PassengerJourneyRepository();
        IMetroStationRepository<MetroStation, String> metroStationRepository  = new MetroStationRepository();
        IPassengerJourneyService passengerJourneyService = new PassengerJourneyService(passengerJourneyRepository);
        IMetroCardService<MetroCard> metroCardService = new MetroCardService(metroCardRepository);

        private final RechargeProcessor rechargeProcessor = new RechargeProcessor();

        private final DiscountCalculator discountCalculator = new DiscountCalculator(RETURN_JOURNEY_DISCOUNT_PERCENTAGE, PERCENT_CONVERSION_FACTOR);
        IMetroStationService<MetroStation> metroStationService = new MetroStationService(metroStationRepository, metroCardService,
                passengerJourneyService, passengerJourneyRepository, discountCalculator, rechargeProcessor, PERCENT_CONVERSION_FACTOR, SERVICE_FEE_PERCENTAGE);
        IConsolePrinterService consolePrinterService = new ConsolePrinterService(metroStationRepository, passengerJourneyService);

        public void loadPassengerFares() {
            PassengerFaresLoader passengerFaresLoader = new PassengerFaresLoader(passengerJourneyRepository);
            passengerFaresLoader.loadPassengerFares();
        }

        public void loadMetroStationsData() {
            MetroStationDataLoader metroStationDataLoader = new MetroStationDataLoader(metroStationRepository);
            metroStationDataLoader.loadMetroStationsData();
        }

        ICommand createBalanceCommand = new BalanceCommand(metroCardService);
        ICommand createCheckInCommand = new CheckInCommand(metroStationService);
        ICommand createPrintSummaryCommand = new PrintSummaryCommand(consolePrinterService);
        CommandInvoker commandInvoker = new CommandInvoker();

        public CommandInvoker getCommandInvoker(){
            commandInvoker.register(Command.BALANCE, createBalanceCommand);
            commandInvoker.register(Command.CHECK_IN, createCheckInCommand);
            commandInvoker.register(Command.PRINT_SUMMARY, createPrintSummaryCommand);
            return commandInvoker;
        }
    }
