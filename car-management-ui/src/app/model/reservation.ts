export interface Reservation {
    id?: string;
    name: string;
    location: string;
    contact_number: string;  // Correct property name
    license_number: string;   // Correct property name
    date_hour: string;        // Ensure this property exists in the interface
    date_hour_fim: string;    // Ensure this property exists in the interface
    car_id: string;
}
