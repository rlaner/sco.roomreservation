import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {NavigationDetailsPage} from '../roomDetail/roomDetail'

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  constructor (public nav:NavController) {}
  rooms =[
    {
    name:"SALA 1",
    adresse: "Via G.Verdi, 36, Trento",
    place:20,
    destination :"conferenze",
    dotation : "pc e proiettore",
    reference:"Mr gianni",
    telefone:"3542987716",
    fax:"046159624",
    mail:"ioj@gmail.com"
  },
    {
    name:"SALA 2",
    adresse: "Via G.Verdi, 36, Trento",
    place:10,
    destination :"conferenze",
    dotation : "pc e proiettore",
    reference:"Signor Luigi",
    telefone:"5464646",
    fax:"046158944",
    mail:"Luigi@gmail.com"
    }]

     openNavDetailsPage(item) {
    this.nav.push(NavigationDetailsPage, { room: item });

  }
 
  }



