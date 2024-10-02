import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CarService } from '../../service/car.service';
import { FormsModule,NgForm  } from '@angular/forms'; // Import FormsModule
import { MenuComponent } from '../../menu/menu.component'; // Import the MenuComponent
import { DatePipe } from '@angular/common';
import { ReservationsCars } from '../../model/reservations_cars';

@Component({
  selector: 'app-index-booking',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, MenuComponent],
  templateUrl: './index-booking.component.html',
  styleUrl: './index-booking.component.css',
  providers: [DatePipe] 
})
export class IndexBookingComponent {
  reservations: ReservationsCars[] = [];

  constructor(public carService: CarService, private datePipe: DatePipe) { }

  ngOnInit(): void {  
    this.loadRservations(); 
  }


  loadRservations(): void {
    this.carService.getAllReservations().subscribe((data: ReservationsCars[])=>{
      this.reservations = data;
    });
  }

  deleteReservation(rersId: string | undefined): void {
    if (confirm('Are you sure you want to delete this Reservation with id '+rersId+'?')) { // Confirm before deletion
      
    }
  }
}
