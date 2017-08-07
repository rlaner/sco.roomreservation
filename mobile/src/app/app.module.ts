import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import {NavigationDetailsPage} from '../pages/roomDetail/roomDetail'
import {PrenotazionePage} from'../pages/prenotazioni/prenotazioni';
import {AccediPage} from '../pages/accedi/accedi';
import { CreditsPage } from '../pages/credits/credits';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    NavigationDetailsPage,
PrenotazionePage,
AccediPage,
CreditsPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp),
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
    NavigationDetailsPage,
    PrenotazionePage,
AccediPage,
CreditsPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
