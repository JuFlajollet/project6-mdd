import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../models/topic';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = 'api/topic';

  constructor(private httpClient: HttpClient) {}

  public findAll(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathService);
  }

  public findById(id: string): Observable<Topic> {
    return this.httpClient.get<Topic>(`this.pathService/${id}`);
  }
}
