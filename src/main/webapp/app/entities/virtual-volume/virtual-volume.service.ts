import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVirtualVolume } from 'app/shared/model/virtual-volume.model';

type EntityResponseType = HttpResponse<IVirtualVolume>;
type EntityArrayResponseType = HttpResponse<IVirtualVolume[]>;

@Injectable({ providedIn: 'root' })
export class VirtualVolumeService {
    private resourceUrl = SERVER_API_URL + 'api/virtual-volumes';

    constructor(private http: HttpClient) {}

    create(virtualVolume: IVirtualVolume): Observable<EntityResponseType> {
        return this.http.post<IVirtualVolume>(this.resourceUrl, virtualVolume, { observe: 'response' });
    }

    update(virtualVolume: IVirtualVolume): Observable<EntityResponseType> {
        return this.http.put<IVirtualVolume>(this.resourceUrl, virtualVolume, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVirtualVolume>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVirtualVolume[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
