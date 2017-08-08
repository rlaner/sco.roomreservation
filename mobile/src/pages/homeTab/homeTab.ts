 import { Platform } from 'ionic-angular';
 import{ Component, OnInit } from '@angular/core';
 import {HomeListPage } from '../homeList/homeList'
 import {HomeMapPage} from '../homeMap/homeMap'
@Component({
  selector: 'page-homeTab',
  templateUrl: 'homeTab.html',
  
})


export class HomeTabPage {
     homeList = HomeListPage;
    homeMap = HomeMapPage;
   isAndroid: boolean = false;
   constructor(platform: Platform) 
   { 
     this.isAndroid = platform.is('android');
    }
    }
