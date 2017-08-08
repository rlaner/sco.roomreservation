import { Injectable } from '@angular/core';
import {Room} from '../app/rooms';
import {ROOMS} from '../component/mock-rooms';

@Injectable()
export class RoomService {

getRooms(): Promise<Room[]> {
    return Promise.resolve(ROOMS);
  }

  // See the "Take it slow" appendix
  getHeroesSlowly(): Promise<Room[]> {
    return new Promise(resolve => {
     
      setTimeout(() => resolve(this.getRooms()), 2000);
    });
  }
}
   

