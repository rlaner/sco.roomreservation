import { Component, OnInit } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {NavigationDetailsPage} from '../roomDetail/roomDetail'
import {RoomService} from '../../services/room.service'
import {Room} from '../../app/rooms';
import { LoadingController } from 'ionic-angular';


@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
  
})
export class HomePage implements OnInit {
   
 
    rooms: Room[];
  constructor (public nav:NavController, public roomService: RoomService, public loadingCtrl: LoadingController) {}
    
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



