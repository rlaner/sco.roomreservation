import { Component, ViewChild } from '@angular/core';
import { Nav, Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import {HomeMapPage} from '../pages/homeMap/homeMap'
import { HomeTabPage } from '../pages/homeTab/homeTab';
import {AccediPage} from '../pages/accedi/accedi';
import {PrenotazionePage} from '../pages/prenotazioni/prenotazioni';
import { CreditsPage } from '../pages/credits/credits';
@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;

  rootPage: any = HomeTabPage;

  pages: Array<{icon: string, title: string, component: any}>;

  constructor(public platform: Platform, public statusBar: StatusBar, public splashScreen: SplashScreen) 
  {
    this.initializeApp();

    // used for an example of ngFor and navigation
    this.pages = [
      {icon: 'home',  title: 'Home', component: HomeTabPage },
      {icon: 'calendar', title: 'Prenotazioni', component:PrenotazionePage},
       {icon: 'home', title: 'Accedi', component:AccediPage},
       {icon: 'information-circle', title: 'Credits', component:CreditsPage},
    ];

  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  openPage(page) {
    // Reset the content nav to have just this page
    // we wouldn't want the back button to show in this scenario
    this.nav.setRoot(page.component);
  }
}
