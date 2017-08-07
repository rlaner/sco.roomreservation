import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {HomePage} from '../home/home'

@Component({
  templateUrl: 'roomDetail.html',
 
})
export class NavigationDetailsPage {
  room;
  
 

  constructor(params: NavParams) {
    this.room = params.data.room;
  
  }

}
