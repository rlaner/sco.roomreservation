import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule , OnInit} from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';

import { MyApp } from './app.component';
import {HomeTabPage } from '../pages/homeTab/homeTab'
import { HomeListPage } from '../pages/homeList/homeList';
import {NavigationDetailsPage} from '../pages/roomDetail/roomDetail'
import {PrenotazionePage} from'../pages/prenotazioni/prenotazioni';
import {AccediPage} from '../pages/accedi/accedi';
import { CreditsPage } from '../pages/credits/credits';
import {RoomService} from '../services/room.service'
import {HomeMapPage} from '../pages/homeMap/homeMap'
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

@NgModule({
  declarations: [
    MyApp,
    HomeTabPage,
    HomeListPage,
    NavigationDetailsPage,
PrenotazionePage,
AccediPage,
CreditsPage,
HomeMapPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp),
    
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomeTabPage,
    HomeListPage,
    NavigationDetailsPage,
    PrenotazionePage,
AccediPage,
CreditsPage,
HomeMapPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    RoomService,
    
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
