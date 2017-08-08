 import { Platform } from 'ionic-angular';
 import{ Component, OnInit } from '@angular/core';
 import {NavigationDetailsPage} from '../roomDetail/roomDetail'
 

 @Component({
  selector: 'page-home',
  templateUrl: 'detailTab.html',
  
})


export class DetailTabPage {
     detailPage = NavigationDetailsPage;
    calendarPage = NavigationDetailsPage;
   isAndroid: boolean = false;
   constructor(platform: Platform) 
   { 
     this.isAndroid = platform.is('android');
    }
    }
