import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {NavigationDetailsPage} from '../roomDetail/roomDetail'
import {RoomService} from '../../services/room.service'
import {Room} from '../../app/rooms';
import { LoadingController } from 'ionic-angular';
import {AccediPage} from '../accedi/accedi';


@Component({
  selector: 'page-home-list',
  templateUrl: 'homeList.html',
  
})


export class HomeListPage implements OnInit {
   rootPage= AccediPage;
   
 
    rooms: Room[];
  constructor (public nav:NavController, public roomService: RoomService, public loadingCtrl: LoadingController) {
 
  }
    
  openNavDetailsPage(item) {
    this.nav.push(NavigationDetailsPage, { room: item });
   }

 ngOnInit(): void {
    this.getRooms();
  }

 
getRooms(): void {
    let loading = this.loadingCtrl.create({
      content: 'Please wait...'
  });
  loading.present();
     this.roomService.getRooms().then(Room =>
      { 
        this.rooms = Room;
        loading.dismiss();
      });
      
    }
  }
  
// import { Component } from '@angular/core';

// import { Platform } from 'ionic-angular';


// @Component({
//   template: `
//     <ion-header>
//       <ion-navbar [color]="isAndroid ? 'danger' : 'primary'">
//         <ion-title>Tabs</ion-title>
//       </ion-navbar>
//     </ion-header>
//     <ion-content>
//     </ion-content>
// `})
// export class TabIconContentPage {
//   isAndroid: boolean = false;

//   constructor(platform: Platform) {
//     this.isAndroid = platform.is('android');
//   }
// }

// @Component({
//   template: `
//   <ion-tabs class="tabs-icon" [color]="isAndroid ? 'danger' : 'primary'">
//     <ion-tab tabIcon="contact" [root]="rootPage"></ion-tab>
//     <ion-tab tabIcon="compass" [root]="rootPage"></ion-tab>
//     <ion-tab tabIcon="analytics" [root]="rootPage"></ion-tab>
//     <ion-tab tabIcon="settings" [root]="rootPage"></ion-tab>
//   </ion-tabs>
// `})
// export class HomePage {
//   rootPage = TabIconContentPage;

//   isAndroid: boolean = false;

//   constructor(platform: Platform) {
//     this.isAndroid = platform.is('android');
//   }
// }


