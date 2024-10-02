import { Component, OnInit } from '@angular/core';
import { CarService } from '../../service/car.service';
import { ReservationsCars } from '../../model/reservations_cars';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute  } from '@angular/router';
import { DatePipe } from '@angular/common';
import { MenuComponent } from '../../menu/menu.component'; // Import the MenuComponent
@Component({
  selector: 'app-view-booking',
  standalone: true,
  imports: [CommonModule, RouterModule, MenuComponent],
  templateUrl: './view-booking.component.html',
  styleUrl: './view-booking.component.css',
  providers: [DatePipe]
})
export class ViewBookingComponent implements OnInit {
  reservation: any
  id?: number

  constructor(private route: ActivatedRoute, public carService: CarService) { }
  ngOnInit(): void {
    // Get the car ID from the route

    const reservationIdString = this.route.snapshot.paramMap.get('rersId');
    this.id = reservationIdString !== null ? +reservationIdString : 0;

    // Fetch car details using the ID
    this.carService.getReservationById(this.id).subscribe({
      next: (data) => { this.reservation = data },
      error: (error) => { console.error(error); }
    });
  }
}
