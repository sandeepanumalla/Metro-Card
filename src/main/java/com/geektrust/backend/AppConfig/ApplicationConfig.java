    package com.geektrust.backend.AppConfig;

    import com.geektrust.backend.Utils.DiscountCalculator;
    import com.geektrust.backend.Utils.RechargeProcessor;
    import com.geektrust.backend.commands.*;
    import com.geektrust.backend.data.LoadProperties;
    import com.geektrust.backend.data.MetroStationDataLoader;
    import com.geektrust.backend.data.PassengerFaresLoader;
    import com.geektrust.backend.entities.MetroCard;
    import com.geektrust.backend.entities.MetroStation;
    import com.geektrust.backend.repositories.*;
    import com.geektrust.backend.service.*;

    public class ApplicationConfig {

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

        int percentageConversionFactor = Integer.parseInt(LoadProperties.getProperty("percentage.conversion.factor"));
        long serviceFeePercentage = Long.parseLong(LoadProperties.getProperty("service.fee.percentage"));
        long discountPercentage = Long.parseLong(LoadProperties.getProperty("return.journey.discount.percentage"));

        private final DiscountCalculator discountCalculator = new DiscountCalculator(discountPercentage, percentageConversionFactor);
        IMetroStationService<MetroStation> metroStationService = new MetroStationService(metroStationRepository, metroCardService,
                passengerJourneyService, passengerJourneyRepository, discountCalculator, rechargeProcessor, percentageConversionFactor, serviceFeePercentage);
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
