import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  templateUrl: 'roomDetail.html',
 
})
export class NavigationDetailsPage {
  room;
  
 

  constructor(params: NavParams) {
    this.room = params.data.room;
  
  }

}
