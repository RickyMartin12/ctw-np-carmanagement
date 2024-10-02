import { Component } from '@angular/core';
import { MenuComponent } from '../../menu/menu.component'; // Import the MenuComponent

@Component({
  selector: 'app-index-booking',
  standalone: true,
  imports: [MenuComponent],
  templateUrl: './index-booking.component.html',
  styleUrl: './index-booking.component.css'
})
export class IndexBookingComponent {

}
